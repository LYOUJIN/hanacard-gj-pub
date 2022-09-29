package com.exflyer.oddi.user.api.user.account.service;

import com.exflyer.oddi.user.api.coupon.service.CouponPromotionService;
import com.exflyer.oddi.user.api.mustad.dao.MustadMapper;
import com.exflyer.oddi.user.api.mustad.dto.MemeberAdvStateResult;
import com.exflyer.oddi.user.api.user.account.dto.*;
import com.exflyer.oddi.user.api.user.auth.service.PassWordEncrypt;
import com.exflyer.oddi.user.api.user.auth.service.PasswordService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.message.service.MessageService;
import com.exflyer.oddi.user.models.*;
import com.exflyer.oddi.user.repository.*;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.repository.jpa.MemberTermsRepository;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.exflyer.oddi.user.share.LocalDateUtils;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Component
@Slf4j
public class MemberAccountService {

  @PersistenceContext
  EntityManager em;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PhoneAuthRepository phoneAuthRepository;

  @Autowired
  private MemberCompanyRepository memberCompanyRepository;

  @Autowired
  private FilesRepository filesRepository;

  @Autowired
  private MemberPasswordEncoder memberPasswordEncoder;

  @Autowired
  private AesEncryptor aesEncryptor;

  @Autowired
  private PassWordEncrypt passWordEncrypt;

  @Autowired
  private MemberTermsRepository memberTermsRepository;

  @Autowired
  private PasswordService passwordService;

  @Autowired
  private WithdrawalMemberRepository withdrawalMemberRepository;

  @Autowired
  private CouponPromotionService couponPromotionService;

  @Autowired
  private CouponPromotionRepository couponPromotionRepository;

  @Autowired
  private MustadMapper mustadMapper;

  @Transactional
  public void addMember(MemberAddReq memberAddReq) throws ApiException {
    // 아이디 중복 체크
    if (isDuplicationId(memberAddReq.getEmail(), memberAddReq.getProvider())) {
      throw new ApiException(ApiResponseCodes.DUPLICATE);
    }

    if (!isConfirmPhoneNumberAuth(memberAddReq.getPhoneNumber())) {
      throw new ApiException(ApiResponseCodes.NOT_VERIFICATION);
    }
    phoneAuthRepository.deleteById(memberAddReq.getPhoneNumber());
    memberAddReq.setPassword(passWordEncrypt.encryptPassword(memberAddReq.getPassword()));
    memberAddReq.setId(idConvertToUuid());

    Member member = new Member(memberAddReq);
    memberRepository.save(member);

    List<MemberTerms> memberTerms = new ArrayList<>();

    for(TermsReq term: memberAddReq.getTerms()) {
      memberTerms.add(new MemberTerms(member.getId(), term.getTermsSeq(), term.getTermsAgree()));
    }
    memberTermsRepository.saveAll(memberTerms);

    //가입쿠폰발급(가입,정액)
    CouponPromotion promotionList = couponPromotionRepository.findAllBySeq("PTC001", "PDT001");
    if(promotionList != null) {
      couponPromotionService.saveCoupon(member.getId(), promotionList);
    }
  }

  public String idConvertToUuid() {
    return Base64.getUrlEncoder().withoutPadding().encodeToString(UUID.randomUUID().toString().getBytes());
  }

  public boolean isDuplicationId(String email, String provider) {
    return memberRepository.isDuplicationId(email,provider) > 0 ? true : false;
  }

  public String sendVerificationNumber(String phoneNumber)
    throws IOException, ApiException {
    final int VERIFICATION_NUMBER_LENGTH = 6;
    VerificationNumberReq verificationNumberReq = new VerificationNumberReq(phoneNumber,
      RandomStringUtils.randomNumeric(VERIFICATION_NUMBER_LENGTH));
    String message = String.format("[머스타드 광고] 인증 번호는 [%s] 입니다.", verificationNumberReq.getVerificationNumber());
    log.debug("인증번호 : {} ", verificationNumberReq.getVerificationNumber());
    messageService.send(message, verificationNumberReq.getPhoneNumber(),"user-api");

    PhoneAuth phoneAuth = new PhoneAuth(verificationNumberReq, aesEncryptor);
    phoneAuthRepository.save(phoneAuth);

    return verificationNumberReq.getVerificationNumber();
  }

  public boolean validVerificationNumber(VerificationNumberReq verificationNumberReq) throws ApiException {
    Optional<PhoneAuth> phoneAuthOptional = phoneAuthRepository.findById(verificationNumberReq.getPhoneNumber());
    PhoneAuth phoneAuth = phoneAuthOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));

    // 만료 시간 체크
    long durationSecond = Duration.between(LocalDateUtils.krNow(), phoneAuth.getAuthExpiredTime())
      .toSeconds();

    if (durationSecond < 0) {
      throw new ApiException(ApiResponseCodes.EXPIRED_VERIFICATION_NUMBER);
    }
    boolean isConfirm = phoneAuth.getAuthNumber().equals(verificationNumberReq.getVerificationNumber());
    if (isConfirm) {
      phoneAuth.setConfirm(true);
      phoneAuthRepository.save(phoneAuth);
    }
    return isConfirm;
  }

  public MemberMyAccount findMyInfo(String id) throws ApiException {

    Optional<Member> memberOptional = memberRepository.findById(id);
    Member member = memberOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));
    MemberMyAccount memberMyAccount = new MemberMyAccount(member);
    List<MemberCompany> memberCompanyList = memberCompanyRepository.findAllByMemberId(id);
    for (MemberCompany memberCompany : memberCompanyList) {
      if (memberCompany.getBusinessLicenseFile() != null) {
        Optional<Files> filesOptional = filesRepository.findById(memberCompany.getBusinessLicenseFile());
        memberMyAccount.setCompanyInfo(memberCompany, filesOptional.orElseGet(() -> new Files()));
      }
    }
    return memberMyAccount;
  }

  @Transactional
  public void modifyMyAccount(MemberMyAccountModReq memberMyAccountModReq) throws ApiException {
    Optional<Member> memberOptional = memberRepository.findById(memberMyAccountModReq.getId());
    Member member = memberOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));
    if (!memberMyAccountModReq.getPhoneNumber().equals(member.getPhoneNumber())) {
      if (!isConfirmPhoneNumberAuth(memberMyAccountModReq.getPhoneNumber())) {
        throw new ApiException(ApiResponseCodes.NOT_VERIFICATION);
      }
    }
    member.setInfoByMyAccountModReq(memberMyAccountModReq, memberPasswordEncoder);
    memberRepository.save(member);
//    phoneAuthRepository.deleteById(memberMyAccountModReq.getPhoneNumber());
    List<MemberCompany> companyList = memberCompanyRepository.findAllByMemberId(memberMyAccountModReq.getId());
    for (MemberCompany memberCompany : companyList) {
      memberCompany.setInfoByMyAccountModReq(memberMyAccountModReq);
    }
    memberCompanyRepository.saveAll(companyList);
  }

  public boolean isConfirmPhoneNumberAuth(String phoneNumber) {
    String encryptPhoneNumber = aesEncryptor.encrypt(phoneNumber);
    return phoneAuthRepository.existsByPhoneNumberAndConfirm(encryptPhoneNumber, true);
  }

  @Transactional
  public void modifyMyPasswordChange(MemberPasswordReq req) throws ApiException {

    //비밀번호
    Member member = memberRepository.findByPassword(req.getId());

    //기존비밀번호 맞는지 체크
    boolean isValidPassword = passwordService.validPassword(req.getPassword(), member.getPassword());
    if (!isValidPassword) {
      throw new ApiException(ApiResponseCodes.PASSWORD_CHANGE);
    }

    //신규 비밀번호 체크
    if (!req.getNewPassword().equals(req.getNewChangePassword())) {
      throw new ApiException(ApiResponseCodes.PASSWORD_NEW_CHANGE);
    }
    memberRepository.updateMemberPassword(req.getId(), passWordEncrypt.encryptPassword(req.getNewChangePassword()), LocalDateUtils.krNow());
  }

  @Transactional(rollbackFor = {Exception.class, Error.class})
  public void modifyMyResign(String memberId) throws ApiException {

    String today = LocalDateUtils.krNow().format(DateTimeFormatter.ofPattern(("yyyyMMdd")));
    MemeberAdvStateResult memeberAdvStateResult = mustadMapper.findMemberAdvStateResign(memberId, today);
    if(memeberAdvStateResult.getAd() > 0) {
      throw new ApiException(ApiResponseCodes.ADV_OPERATION);
    }

    if(memeberAdvStateResult.getReady() > 0) {
      throw new ApiException(ApiResponseCodes.ADV_READY);
    }

    Optional<Member> memberOptional = memberRepository.findById(memberId);
    Member member = memberOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));

    WithdrawalMember withdrawalMember = new WithdrawalMember(member);
    withdrawalMemberRepository.save(withdrawalMember);
    memberRepository.deleteById(memberId);
  }

}
