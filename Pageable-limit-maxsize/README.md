## Spring MVC - Pageable 최대 크기 제한 ##

### 0\. 들어가며

Spring MVC에서 페이징 기능을 구현할 때 Pageable을 통해서 많이 구현합니다.

Pageable은 Spring에서 페이징 기능에서 자주 사용하는 기능들을 정의해놓은 인터페이스입니다.

클라이언트에서 어떤 리소스의 요청결과값이 굉장히 많은 경우, 전체 데이터를 한 번에 반환하는 것이 아니라 전체 개수를 일정한 크기의 Page로 나누고 Page의 번호로 Page를 구분하는 형태로 구현되어있습니다.

페이징 API를 구현할 때, 컨트롤러에서 Pageable 타입으로 파라미터를 전달받아서 넘기면 Spring Data JPA를 사용한다면, 따로 다른 객체로 변환해줄 필요없이 바로 사용할 수도 있고 여러가지 장점이 많습니다.

그래서 페이징 기능을 구현할 때 Pageable을 많이 사용하고는 합니다.

페이징 기능을 구현하면 한번에 요청하는 데이터의 개수를 제한해야하는 경우가 많습니다. 데이터 요청량이 많지 않은 기능이 단순한 서비스나 트래픽이 많지 않아 조회기능의 시간이 오래걸려도 상관없는 경우가 아니라면 제한하는 것이 좋습니다. 

### 1\. Pageable 의 최대개수를 제한하는 방법

Pageable에서 한 번에 요청할 수 있는 page의 최대 크기를 설정할 수 있는 몇 가지 방법이 있습니다.

하나씩 살펴보도록 하겠습니다.

#### \- application.properties에서 설정

```
spring.data.web.pageable.max-page-size = 2000
```

첫 번째는 application.properties 혹은 application.yml 파일에 property를 지정하는 방법입니다.

이렇게 하면, Pageable이 사용되는 모든 곳에 적용됩니다.

장점은 한 번에 모두 조절할 수 있다는 것이고, 단점은 API별로 제한해줄 수 없다는 점입니다.

모든 페이징 관련기능을 한꺼번에 관리하기에는 좋지만, 관리자 기능과 회원기능의 최대 요청개수가 달라야 한다던지, API별로 최대 요청 개수가 달라져야 하는 경우에는 사용하기 어려운 방법입니다.

#### \-  DTO 활용

```
@Setter
@Getter
@NoArgsConstructor
public class CustomPageModel {

    @Max(value = 2000, message = "페이지의 최대 크기는 2000을 넘을 수 없습니다.")
    private int size;
    private int pageNumber;
    private Sort sort;
}
```

```
@GetMapping("/api/v1/admin/users")
    public ResponseEntity<CommonResponse<List<UserResponse>>> list(UserSearchRequest userSearch, @Valid CustomPageModel customPageModel) {

        Pageable pageable = PageRequest.of(customPageModel.getPageNumber(), customPageModel.getSize(), customPageModel.getSort());

        Page<User> result = userViewService.findAll(userSearch, pageable);
        List<UserResponse> responseContent = result.getContent().stream().map(User::toResponseDto).toList();

        return ResponseEntity.ok().body(new CommonResponse<>(responseContent, result.getTotalElements()));
    }
```

DTO를 활용하는 방법도 있습니다.

클라이언트가 전송한 파라미터를 Pageable이 아니라 DTO로 받아서 구현할 수도 있습니다.

하지만 Spring Data JPA의 지원을 받기 위해서 Pageable 객체를 다시 생성해주어야 하는 불편함이 있습니다.

또한, Spring에서 기본적으로 제공하는 Sort 클래스를 사용하기 어렵다는 점입니다.

Sort 클래스는 Pageable 에서 정렬에 대한 정보를 저장하는 클래스입니다. 정확히 표현하면 Pageable은 인터페이스므로 Pageable을 구현한 구현체인 PageRequest의 필드입니다.

클라이언트에서 페이징 정보를 얻기 위해 다음과 같은 URL로 리소스를 요청하곤합니다.

```
http://www.api.myservice.com/users/list/page=0&size=100&sort=userid,desc
```

전달하는 쿼리파라미터 중 sort를 보면 정렬의 기준이 되는 컬럼의 이름과 방향을 , 로 구분하여 전달하고 있습니다.

위 URL의 sort 파라미터를 컨트롤러에서 Pageable 타입으로 파라미터를 받으면 Pageable 객체에 바인딩이 정상적으로 되지만 dto를 사용하면 바인딩이 되지 않습니다.

Pageable이 콤마로 구분된 문자열을 Sort 클래스의 필드에 바인딩할 수 있는 이유는  URL 경로의 쿼리 파라미터를 Pageable의 Sort에 매핑시키는 역할을 수행하는 Argument Resolver가 있기 때문입니다.

Argument Resolver가 어떻게 파라미터를 바인딩하는지, 그 과정에 대한 내용은 아래 링크에서 확인할 수 있습니다.

[@ModelAttribute 어노테이션을 생략했을 때 파라미터가 바인딩되는 과정](https://style-tech.tistory.com/20)


하지만 직접 생성해 쿼리 파라미터를 매핑하는 Argument Resolver가 없는 DTO 클래스의 sort 필드에 바인딩시키려면 전달하는 쿼리스트링의 형태를 변경하고 Custom Sort 클래스를 생성하거나 클라이언트에서 전달하는 sort 파라미터를 파싱하고 DTO클래스에 바인딩되도록 직접 구현해야합니다.

직접 구현하는것보다는 Spring에서 지원하는 기능을 사용하는 것이 더 편리하기때문에 추천드리지 않습니다.

하지만, DTO 클래스를 만들면 페이지 최대크기뿐만 아니라 페이지 번호나 정렬등에 관한 다양한 설정을 자유롭게 할 수 있다는 장점은 있습니다.

#### \-  ArgumentResolver 정의

**_0\. 페이지 최대 크기 제한_**

그렇다면 Pageable과 관련된 파라미터를 처리하는 Argument Resolver를 수정하거나 만드는 방법도 생각해 볼 수 있습니다. Spring에서는 개발자가 직접 정의한 Argument Resolver를 등록할 수 있는 기능을 지원하기도 하고, 이미 정의된 Argument Resolver의 메소드를 오버라이딩하여 변경할 수도 있습니다.

![image](https://github.com/sungwoon129/blog-code/assets/43958570/befbec1f-d98c-4ebe-91a1-f02f4974e1f4)

Pageable을 처리하는  Argument Resolver는 PageableHandlerMethodArgumentResolver입니다.

```
public class PageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolverSupport
		implements PageableArgumentResolver {

	private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();
	private SortArgumentResolver sortResolver;

	/**
	 * Constructs an instance of this resolved with a default {@link SortHandlerMethodArgumentResolver}.
	 */
	public PageableHandlerMethodArgumentResolver() {
		this((SortArgumentResolver) null);
	}
    
    // 중간 생략...
    
    	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Pageable.class.equals(parameter.getParameterType());
	}

	@Override
	public Pageable resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {

		String page = webRequest.getParameter(getParameterNameToUse(getPageParameterName(), methodParameter));
		String pageSize = webRequest.getParameter(getParameterNameToUse(getSizeParameterName(), methodParameter));

		Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
		Pageable pageable = getPageable(methodParameter, page, pageSize);

		if (sort.isSorted()) {
			return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}

		return pageable;
	}
}
```

supportsParameter () 는 이 Argument Resolver가 어떤 경우에 동작하는 지에 대한 내용을 정의한 메소드입니다.

메소드 내부를 보면 파라미터의 타입이 Pageable 클래스 타입인 경우에 처리한다는 것을 알 수 있습니다.

resolveArgument() 는 파라미터가 supportParameter()의 조건을 충족한다면 어떤 일을 하는지에 대한 내용이 정의된 메소드입니다. 내부를 살펴보면 웹 요청정보가 담겨있는 webRequest 객체에서 page, pageSize, sort정보를 얻어와서 Pageable 객체를 생성하고 반환한다는 사실을 알 수 있습니다.

그렇다면 이 resolveArgument() 에서 pageable객체를 반환하기전에 pageSize를 검사해서 저희가 원하는 최대 사이즈를 넘어가는 경우에는 요청을 거부하는 방법은 어떨까요?

이렇게 하면 페이지의 최대사이즈를 제한할 수 있을 것 같습니다.

그래서 PageableHandlerMethodArgumentResolver의 resolveArgument를 오버라이딩해서 페이지의 최대사이즈를 제한하는 로직을 추가해보겠습니다.

```
public class CustomPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

	@Override
    public Pageable resolveArgument(final MethodParameter methodParameter, final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {

        String pageSize = webRequest.getParameter(getParameterNameToUse(getSizeParameterName(), methodParameter));
        validate(pageSize);
        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private void validate(final String pageSize) {
        if (pageSize != null && Integer.parseInt(pageSize) > 999) {
            throw new PageSizeOutOfBoundsException("페이지의 최대 size는 999를 넘을 수 없습니다.");
        }
    }
}
```

다음에는 우리가 정의한 Custom 클래스를 스프링의 argumentResolver로 등록해 줘야 합니다.

```
@NoArgsConstructor
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomPageableHandlerMethodArgumentResolver());
    }
}
```

argumentResolver를 추가하기 위해서는 WebMvcConfigurer 인터페이스를 상속받아 addArgumentResolver 메소드를 오버라이딩해서 Spring에 미리 등록되어있는 HandlerMethodArgumentResolver 리스트에 추가해줘야 합니다.

리스트에 직접 구현한 ArgumentResolver를 등록해주면 준비는 끝났습니다.

다음은 PageableHandlerMethodArgumentResolver는 파라미터가 Pageable 클래스타입인경우에만 동작하므로, 컨트롤러에서도 Pageable을 파라미터로 받도록 하겠습니다.

```
    @GetMapping("/api/v1/admin/users")
    public ResponseEntity<CommonResponse<List<UserResponse>>> list(UserSearchRequest userSearch,Pageable pageable) {

        Page<User> result = userViewService.findAll(userSearch, pageable);
        List<UserResponse> responseContent = result.getContent().stream().map(User::toResponseDto).toList();

        return ResponseEntity.ok().body(new CommonResponse<>(responseContent, result.getTotalElements()));

    }
```

이제 구현한 클래스들이 의도한대로 페이지의 최대 개수(여기서는 999)를 넘는 요청을 하면 예외를 발생시키는지 테스트해보겠습니다.

```
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class AdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository repository;

    @DisplayName("회원 검색 API 요청시 페이징 size의 값이 1000이상이면 예외를 반환한다.")
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findByConditionsTest_fail() {
        //given
        repository.save(User.builder()
                .id(repository.nextUserId())
                .nickname("test")
                .build());


        //when,then
        assertThrows(ServletException.class, () -> mvc.perform(get("/api/v1/admin/users?size=1000&sort=id,desc")).andDo(print()));
    }
}
```

페이징 정보를 쿼리파라미터로 전달하는 요청을 보내는 간단한 테스트코드입니다.

테스트코드를 실행하면...

![image](https://github.com/sungwoon129/blog-code/assets/43958570/b51b454e-ec83-4b07-a090-d71f6ed97206)

통과했습니다. 의도한대로 동작하는 모습을 확인할 수 있습니다.

**_1.  API별로 다른 페이지 최대크기제한 설정_**

여기까지만 보았을 때는 페이지의 전체크기를 제한하는 단순한 기능인데, 너무 많은 코드를 작성해야하니 불편합니다.

차라리 application.properties에 간단하게 한 줄 작성하기만 하면 구현할 수 있는 기능을 번거롭게 하는 것처럼 보입니다.

그래서 이번에는 API별로 페이지의 최대 크기를 다르게 설정할 수 있도록 고쳐보겠습니다.

어노테이션을 활용해보면 어떨까요?

Pageable 파라미터 앞에 어노테이션을 붙이고 어노테이션에 내가 원하는 최대 크기를 정할 수 있으면 좋을 것 같습니다.

그렇다면 먼저 어노테이션을 만들어보겠습니다.

```
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LimitedPageSize {
    int maxSize() default 999;
}
```

LimitedPageSize 어노테이션의 타겟은 Pageable 파라미터앞에 붙일 것이므로 ElementType.PARAMETER로 설정해주고, 서버가 실행하는 도중 클라이언트의 요청에 대해 페이지의 최대크기를 검사해야하므로 실행환경은 RUNTIME으로 정해줍니다. 

그리고 기본값을 줘서 어노테이션만 선언하고 값을 선언하지 않아도 기본값으로 동작하도록 하겠습니다.

```
public class CustomPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

    @NonNull
    @Override
    public Pageable resolveArgument(@NonNull MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer,
                                    @NotNull NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {

        final Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        if(methodParameter.hasParameterAnnotation(LimitedPageSize.class)) {
            final int maxSize = methodParameter.getParameterAnnotation(LimitedPageSize.class).maxSize();
            validate(pageable, maxSize);
        }

        return pageable;
    }

    private void validate(final Pageable pageable, final int maxSize) {
        if(pageable.getPageSize() > maxSize) {
            throw new PageSizeOutOfBoundsException("page size는 최대 " +  maxSize + "이하의 값을 가져야합니다.");
        }
    }
}
```

다음으로, 우리가 오버라이딩한 resolveArgument의 내용을 조금 수정해주겠습니다.

왜냐하면 어노테이션에 전달된 값에 따라 다른 최대 크기를 가져야하기때문에 그 부분에 대한 로직의 변경이 필요하기때문입니다.

추가적으로 부모클래스의 resolveArgument를 호출하면서 pageSize를 다시 요청하는 부분에 중복이 있어 제거하기 위해서 Pageable객체를 먼저 생성하고 Pageable 객체에서 maxSize(페이지의 최대 크기)를 얻어오도록 하겠습니다.

Pageable 타입의 파라미터에 LimitedPageSize 어노테이션이 있는 경우에 어노테이션에 전달되거나 기본값인 maxSize값과 클라이언트가 요청한 파라미터값을 비교해서 maxSize값을 넘을 경우 예외를 발생시키도록 변경하였습니다.

```
@GetMapping("/api/v1/admin/users")
    public ResponseEntity<CommonResponse<List<UserResponse>>> list(UserSearchRequest userSearch, @LimitedPageSize(maxSize = 100) Pageable pageable) {

        Page<User> result = userViewService.findAll(userSearch, pageable);
        List<UserResponse> responseContent = result.getContent().stream().map(User::toResponseDto).toList();

        return ResponseEntity.ok().body(new CommonResponse<>(responseContent, result.getTotalElements()));

    }
```

마지막으로 컨트롤러에 Pageable 파라미터앞에 어노테이션을 붙여주고 테스트해보도록 하겠습니다.

![image](https://github.com/sungwoon129/blog-code/assets/43958570/5630b76f-695f-4813-9278-42c41c3af002)

@LimitedPageSize 어노테이션에 최대크기를 100으로 전달하였으므로, 예외를 발생시키기 위해서 101개의 데이터를 요청해보겠습니다.

![image](https://github.com/sungwoon129/blog-code/assets/43958570/793ea4cb-9fac-4f72-86c1-894c0ecb9bdf)

테스트가 정상적으로 통과된 모습을 확인할 수 있습니다.

### 2\. 마무리

여기까지해서 Pageable의 최대크기를 제한할 수 있는 여러가지 방법들에 대해서 알아보았습니다.

설정파일에 property를 추가하는 간단한 방법부터 ArgumentResolver를 정의해서 API별로 다른 최대크기를 적용하는 방법도 있었습니다. ArgumentResolver 정의하는 방법은 작성할 코드가 많았지만 그만큼 확장성이 있습니다. 여기서는 API별로 페이지의 최대크기를 제한하는 기능만 구현하였지만, 활용하기에 따라 더 복잡한 기능도 resolveArgument() 에서 구현할 수 있을 것입니다. 

프로젝트의 요구사항에 맞춰 가장 적합한 방법을 선택해서 사용하는 것이 가장 현명하다고 생각합니다.