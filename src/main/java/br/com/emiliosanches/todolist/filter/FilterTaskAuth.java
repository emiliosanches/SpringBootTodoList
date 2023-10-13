package br.com.emiliosanches.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.emiliosanches.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var servletPath = request.getServletPath();

    if (!servletPath.endsWith("/")) servletPath += "/";

    if (servletPath.equals("/users/")) {
      filterChain.doFilter(request, response);
      return;
    }

    var authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null) {
      response.sendError(401);
      return;
    }
  
    var basicToken = authorizationHeader.substring("Basic".length()).trim();

    byte[] authDecoded = Base64.getDecoder().decode(basicToken);

    var authString = new String(authDecoded);

    var credentials = authString.split(":");

    String username = credentials[0];
    String password = credentials[1];

    var user = this.userRepository.findByUsername(username);

    if (user == null) {
      response.sendError(401);
      return;
    }

    var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());

    if (!result.verified) {
      response.sendError(401);
      return;
    }

    request.setAttribute("userId", user.getId());

    filterChain.doFilter(request, response);
  }

}
