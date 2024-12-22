package net.ljw.backend.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import net.ljw.backend.Dto.MemberDto;
import net.ljw.backend.entity.Items;
import net.ljw.backend.entity.Members;
import net.ljw.backend.repository.MemberRepository;
import net.ljw.backend.service.JwtService;
import net.ljw.backend.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/api/member/login")
    public ResponseEntity<Object> members(
            @RequestBody MemberDto members
            ,HttpServletResponse res
    ){
        Members member = memberRepository.findByEmailAndPassword(members.getEmail(), members.getPassword());
        if(member != null) {
            Cookie cookie = new Cookie("token", jwtService.getToken("id", member.getId()));
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            res.addCookie(cookie);
            return new ResponseEntity<>(member.getId(), HttpStatus.OK);
        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/member/check")
    public ResponseEntity<Object> check(
            @CookieValue(value = "token", required = false) String token
    ){
        System.out.println("token:"+token);
        Claims claims= jwtService.getClaims(token);
        if(claims != null){
            int id = Integer.parseInt(claims.get("id").toString());
            System.out.println("id:"+id);
            return new ResponseEntity<>(id, HttpStatus.OK);

        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/api/member/logout")
    public ResponseEntity<Object> logout(
            @CookieValue(value = "token", required = false) String token
            ,HttpServletResponse res
    ){

        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
