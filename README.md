# Next MVC Library 0.0.1
편합니다!


##GET
pom.xml에 아래의 레파지토리와 Dependency설정을 추가합니다.

###Repository
    <repository>
        <id>next-mvn-repo</id>
    	<url>https://raw.github.com/zerohouse/next/mvn-repo/</url>
	</repository>

###Dependency
	<dependency>
		<groupId>at.begin</groupId>
		<artifactId>next-mvc</artifactId>
		<version>0.0.1</version>
	</dependency>


#MVC

## Example Usage

    @Controller
    @Mapping(value="/api/user", before="loginCheck")
	public class UserController {
		@Build
		GDAO<User> userDAO;
	
		@Build
		@ImplementedBy(DeleteRight.class)
		Right right;
		
		@Mapping(value = "/login", method = Method.POST, before="!loginCheck")
		public Response login(@JsonParameter("user") User user, HttpSession session) {
			User fromDB = userDao.find(user.getEmail());
			if (fromDB == null)
				return new Json(true, "가입하지 않은 이메일입니다.", null);
			if (!fromDB.getPassword().equals(user.getPassword()))
				return new Json(true, "비밀번호가 다릅니다.", null);
			session.setAttribute("user", fromDB);
			return new Json(fromDB);
		}
	}

## Method Return Type별 응답
#### 1. Response.class Interface

    new Json(JsonObject);
    new Jsp(Jsp파일명);
    new StaticFile(파일명); // webapp/파일
    
#### 2-1. String이 forward:로 시작할때 :뒤의 Path로 forward(클라이언트에서 주소 바뀌지 않음)
#### 2-2. String이 redirect:로 시작할때, :뒤의 Path로 reidrect(클라이언트 주소 바뀜) 
#### 2-3. String이 error:(no):(message)로 시작할때, :뒤의 메시지로 에러를 리턴
	ex)
	return "forward:/index.html" // forward to : /index.html 
	return "redirect:/index.html" // redirect to : /index.html 
	return "error:404" // 404에러 리턴
	return "error:404:페이지가 없습니다." // 404에러 리턴 + 에러메시지 리턴
#### 4. Object Return시 new Json(Object)로 간주 JSON으로 응답함.
	ex)
	return "/index.html" // JsonObject Return {"response":"/index.html"}
#### 5. 리턴값 없으면 empty JSON 오브젝트 리턴



### Annotations
#### @Controller [클래스 레벨]
컨트롤러 클래스에 사용

#### @Mapping [클래스, 메서드 레벨]
Url 매핑 정보를 정의

    String[] value() default ""; // 매핑될 url들
	String[] before() default ""; // 해당 메서드를 실행하기 전 실행될 메서드 
	String[] after() default ""; // 해당 메서드를 실행한 후 실행될 메서드
	String[] method() default "GET"; // 매핑될 메서드(Post, Get, Put, Delete등) 
	
### Uri 변수의 사용
	모든 파라미터를 받을때 {}와 *를 사용합니다.
	@Mapping("/{variableName}/*");
     *와 {}의 차이점은 변수를 꺼낼 수 있느냐의 여부입니다.
	http.getUriVariable("variableName");
	혹은 파라미터에 @UriVariable("variableName") String uri로 사용가능합니다.

#### @HttpMethods [클래스 레벨]
@HttpMethod메서드 클래스에 선언.

#### @HttpMethod [메서드 레벨]
공통적으로 사용할 메서드 정의 @Mapping의 before, after에서 사용

    String value() default ""; // 매핑될 이름 값이 없으면 메서드 이름으로 매핑
    
#### @Parameter, @JsonParameter, @SessionAttribute, @DB(keyParameter="?"), @Stored [파라미터 레벨]

#### example
    @Mapping(value = "/update", before = "loginCheck", method = Method.POST)
    public void updatePost(@Parameter("userId") String parameter,
     		@FromDB(keyParameter="userId") User user2,
              @JsonParameter("Post") Post post,
              @SessionAttribute("user") User user, @Stored List<String> mylist) {
              //Stored의 경우
              //Store store를 꺼내 저장한 속성을 뺄 수 있음.
    }
    


### UploadFile.class
쉬운 파일 업로드. 파라미터에서 받고, 파일이름 바꾸고, 세이브.

#### File Upload Example
    
    
    @Controller
    @Mapping(value = "/api/upload")
    public class UploadController {
    
        @Build
    	DAO dao;
    
    	@Mapping(value = "/profilePhoto", method = Method.POST)
    	public Object profile(@SessionAttribute("user") User user,
            @Parameter("photo") UploadFile file) throws IOException {
    		file.setFileName("profile_" + user.getEmail().replace('@', '_'));
    		user.setPhotoUrl(file.getUriPath());
    		file.save();
    		dao.update(user);
    		return user;
    	}
    
    }


### Http.class Interface
HttpImpl.class, HttpForTest.class
HttpSevlet req와 resp의 Wrapper 클래스, 익셉션제거

#### example
    @Mapping(method = Method.GET, before="loginCheck")
    public void getAnswers(Http http) {
        http.forward("/index")
    }
    
#### vs 직접 사용 하는 경우
    @Mapping(method = Method.GET, before="loginCheck")
    public void getAnswers(HttpServletRequest req, HttpServletResponse res) {
        RequestDispatcher rd = req.getRequestDispatcher(path);
    	try {
			rd.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



# Setting
1. 아래 web.xml을 webapp디렉토리의 WEB-INF폴더 내에 위치.
2. resource폴더 내에 next.json 위치 (기본 세팅을 담당)
3. resource폴더 내에 build.json 위치 (빌드할 오브젝트 JsonFormat)

## web.xml (webapp/WEB-INF/web.xml)
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://java.sun.com/xml/ns/javaee"
    	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
    	http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
    	version="3.0">
    	<listener>
    		<listener-class>next.Next</listener-class>
    	</listener>
    </web-app>


## next-mvc.json (resources/next-mvc.json)
### Setting
	{
	  "basePackage": "",
	  "mappings": [
	    "/api/*",
	    "*.page"
	  ],
	  "url": "localhost:8080",
	  "jspPath": "/WEB-INF/jsp/"
	}



### Default Setting : 기본 세팅은 아래와 같습니다.
	{
	  "basePackage": "",
	  "mappings": [],
	  "characterEncoding": "UTF-8",
	  "url": "",
	  "jspPath": "",
	  "dateFormat": "yyyy-MM-dd HH:mm:ss",
	  "upload": {
	    "location": "uploads/",
	    "tempSaveLocation": "uploads/temp/",
	    //5MB = 1024*1024*5
	    "maxFileSize": 5242880,
	    //25MB = 1024*1024*5*5
	    "maxRequestSize": 26214400,
	    //1MB = 1024*1024
	    "fileSizeThreshold": 1048576
	  }
	}
