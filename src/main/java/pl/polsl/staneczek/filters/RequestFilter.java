package pl.polsl.staneczek.filters;

import pl.polsl.staneczek.model.Instructor;
import pl.polsl.staneczek.model.Student;
import pl.polsl.staneczek.model.User;
import pl.polsl.staneczek.model.UserRole;
import pl.polsl.staneczek.service.InstructorService;
import pl.polsl.staneczek.service.StudentService;
import pl.polsl.staneczek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class RequestFilter implements Filter {

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private InstructorService instructorService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String redirectURI = new String(((HttpServletRequest) servletRequest).getRequestURI());
        StringBuffer redirectURL = new StringBuffer(((HttpServletRequest) servletRequest).getRequestURL().toString());

        if(redirectURI.contains("/login") || redirectURI.equals("/student/add")  || redirectURI.equals("/course/current")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String authHeader = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        if (authHeader != null) {

            String usernameAndPassHash = authHeader.split(" ")[1];
            String usernameAndPass = new String(Base64Coder.decodeString(usernameAndPassHash));
            String email = usernameAndPass.split(":")[0];
            String password = usernameAndPass.split(":")[1];
            User user = userService.findByEmail(email);
            byte[] decodedBytes = Base64.getDecoder().decode(user.getPassword());
            String userPassword = new String(decodedBytes);
            if (email.equals(user.getEmail()) && password.equals(userPassword)) {
                if (user.getUserRole().equals(UserRole.ADMIN)){
                    if(redirectURI.contains("/student/notification") || redirectURI.contains("/instructor/notification") ||redirectURI.contains("/lesson") ||redirectURI.contains("/student/lesson") ||redirectURI.contains("/instructor/lesson") )
                    {
                        ((HttpServletResponse) servletResponse).sendError(401, "Unauthorized");
                    }else{
                    filterChain.doFilter(servletRequest, servletResponse);
                    }
                }
                Student student = studentService.findByUserId(user.getId());
                Instructor instructor=instructorService.findByUserId(user.getId());
                if (student != null) {
                    String studentId = student.getId().toString();
                    if (redirectURI.equals("/student/") ||redirectURI.equals("/student/changePassword/") ||redirectURI.equals("/lesson/") || redirectURI.contains("/course/addStudent/")||redirectURI.equals("/student/course/") || redirectURI.equals("/student/lesson/") ||redirectURI.contains("/student/notification/read/")|| redirectURI.equals("/student/notification/")) {
                        filterChain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
                            @Override
                            public StringBuffer getRequestURL() {
                                return redirectURL.append(studentId);
                            }
                            @Override
                            public String getRequestURI() {
                                return redirectURI.concat(studentId);
                                }
                            }, servletResponse);
                        } else if(redirectURI.contains("/instructor/ratings/") ||redirectURI.contains("/instructor/averageRating/") || redirectURI.contains("/instructor/freeHours") || redirectURI.equals("/rating/add") ||  redirectURI.contains("/instructor/find/") || redirectURI.equals("/vehicle/all")|| redirectURI.contains("/vehicle/find/") || redirectURI.contains("/course/vehicles/") || redirectURI.equals("/instructor/all")){

                        filterChain.doFilter(servletRequest, servletResponse);
                        }else{
                        ((HttpServletResponse) servletResponse).sendError(401, "Unauthorized");
                    }

                    }
                    if(instructor!=null) {

                        String instructorId = instructor.getId().toString();
                        if (redirectURI.equals("/instructor/") || redirectURI.equals("/instructor/find/") || redirectURI.equals("/instructor/changePassword/") || redirectURI.equals("/instructor/lesson/accepted/") || redirectURI.equals("/instructor/lesson/waiting/") || redirectURI.equals("/instructor/notification/")|| redirectURI.equals("/instructor/notification/read/")||redirectURI.equals("/instructor/lesson/accept/")|| redirectURI.equals("/instructor/lesson/deny/") ||redirectURI.equals("/instructor/lesson/")) {

                            filterChain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
                                @Override
                                public StringBuffer getRequestURL() {
                                    return redirectURL.append(instructorId);
                                }
                                @Override
                                public String getRequestURI() {
                                    return redirectURI.concat(instructorId);
                                }
                            }, servletResponse);
                        }
                        else if(redirectURI.equals("/vehicle/all") || redirectURI.equals("/student/all") ){
                            filterChain.doFilter(servletRequest, servletResponse);
                        }else{
                            ((HttpServletResponse) servletResponse).sendError(401, "Unauthorized");
                        }
                    }

                } else {
                  ((HttpServletResponse) servletResponse).sendError(401, "Unauthorized");

                }
            } else {
               ((HttpServletResponse) servletResponse).sendError(401, "Unauthorized");
            }
        }
    }
