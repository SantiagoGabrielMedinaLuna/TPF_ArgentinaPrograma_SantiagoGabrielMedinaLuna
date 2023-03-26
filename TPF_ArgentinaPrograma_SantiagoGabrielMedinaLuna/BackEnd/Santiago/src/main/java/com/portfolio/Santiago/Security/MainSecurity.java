package com.portfolio.Santiago.Security;

/*BUENAS, POSIBLEMENTE EL PROFE QUE TENGA QUE CORREGIR LLEGUE A LEER ESTO,
ME PRESENTO SOY SANTIAGO LUNA TENGO 18 AÑOS Y SOY DE SANTIAGO DEL ESTERO
, LA VERDAD ME PERDI 2 MESES DE ESTUDIO PORQUE
SE ME ROMPIO LA COMPU DEL GOBIERNO DE HACE 5 AÑOS JAJAJA,
POR TEMAS FAMILIARES ESTUVE 2 SEMANAS SIN ESTUDIAR POR "DUELO"(MI ABUELA) CON QUIEN ERA MUY APEGADO
Y LE PROMETI, LLEGAR A SER UN PROFESIONAL EN ESTO DE LA PROGRAMACION ALGUN DIA.
SE QUE NO ES PROBLEMA SUYO, PERO SE ME DIO POR CONTARLO ASI NO PAREZCA UNA PERSONA IRRESPONSABLE
Y QUE LE QUITA LAS OPORTUNIDADES A LOS DEMAS, ESTE INTENTO DE "PROYECTO", ES LO QUE PUDE HACER EN
5 MESES, NO PUDE TERMINAR EL LOGIN, SIMPLEMENTE EL POSTMAN ME DABA INTERNAR ERROR 500 BUSQUE 
POR TODO INTERNET DURANTE 2 SEMANAS Y NO PUDE HACER NADA PARA RESOLVERLO, SE QUE NO MEREZCO APROBAR,
PERO QUERIA PEDIRLE SI NO PODRIA DARME UN 6 POR EL ESFUERZO, Y TODO LOQ UE TUVE QUE PASAR PARA PODER
CONSEGUIR UNA COMPU, QUE A DURAS PENAS ABRE EL NETBEANS, VS CODE, XAMPP Y POSTMAN AL MISMO TIEMPO,
EN EL PROCESO CONSEGUI UNOS 23 PANTALLAZOS AZULES, EL CERTIFICADO SIGNIFICARIA MUCHO PARA MI.
AHORA PUDE RETOMAR MIS ESTUDIOS, Y DARLE A FULL A LO QUE ME GUSTA, QUE ES LA PROGRAMACION Y EL HARDWARE,
SERIA TREMENDO PODER TENER EL CERTIFICADO SOLO COMO COMPENSACION DEL ESFUERZO QUE HICE Y PODER TENERLO EN MI CV
, DESDE YA MUCHISIMAS GRACIAS SI TE DETUVISTE A LEER ESTO. YA PARECE CADENA DE FACEBOOK DE TANTO QUE ESCRIBI.
*/
import com.portfolio.Santiago.Security.Service.UserDetailsImpl;
import com.portfolio.Santiago.Security.jwt.JwtEntryPoint;
import com.portfolio.Santiago.Security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter{
    @Autowired
    UserDetailsImpl userDetailsImpl;
    @Autowired
    JwtEntryPoint jwtEntryPoint;
    
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsImpl).passwordEncoder(passwordEncoder());
    }
}