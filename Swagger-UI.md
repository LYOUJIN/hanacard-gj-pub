## Swagger-UI

코드베이스로  API 문서를 작성해 주는 라이브러리 입니다.

**3.0.0  version 은 position 이 동작하지 않음** 



## 사용방법

### install

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>3.0.0</version>
</dependency>
```



### Usage

API Class (Controller)

```
@Api(tags = "커피머신 매출 API", protocols = "http")
@RestController
@RequestMapping("/coffee")
@Slf4j
public class CoffeeApi {
...
}
```



API Method 

```
@ApiImplicitParams({
	@ApiImplicitParam(name = "bioStarId", value = "바이오스타 계정 ID", dataType = "string", paramType = "path")
})
@ApiOperation(value = "사용자 삭제", notes = "사용자 삭제")
@DeleteMapping("/user/{bioStarId}")
public String deleteUser(@PathVariable("bioStarId") String bioStarId) throws Throwable {
	return bioStar2Service.deleteUser(bioStarId);
}
```



DTO

```
@Data
@ApiModel
public class AccessLogReq {
    @ApiModelProperty(value = "시작시간(UTC)", example = "20210430110000", required = true, position = 0)
    private String fromDate;

    @ApiModelProperty(value = "종료시간(UTC)", example = "20210430235959",position = 1)
    private String toDate;
}
```