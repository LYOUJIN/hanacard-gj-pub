package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "voc_file")
@ApiModel("1:1문의하기 파일 목록")
public class VocFile {

    @EmbeddedId
    private VocFilePk vocFilePk;

    @NotNull
    @ApiModelProperty(value = "reg_date", position = 0)
    private LocalDateTime regDate;

    public VocFile(long vocSeq, long fileSeq) {
        this.vocFilePk = new VocFilePk(vocSeq, fileSeq);
        this.regDate = LocalDateUtils.krNow();
    }

}
