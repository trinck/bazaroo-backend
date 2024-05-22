package org.mts.usersservice.web;

import org.modelmapper.ModelMapper;
import org.mts.usersservice.configs.DefaultClientPref;
import org.mts.usersservice.dtos.AuthInputDTO;
import org.mts.usersservice.dtos.AuthOutputDTO;
import org.mts.usersservice.dtos.ClientOutputDTO;
import org.mts.usersservice.dtos.EmployeeOutputDTO;
import org.mts.usersservice.entities.*;
import org.mts.usersservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("auths")
public class AuthController {



    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IConnectionService iConnectionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAuthService iAuthService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IClientService clientService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IPreferenceService preferenceService;
    @Autowired
    DefaultClientPref clientPref;

    @PostMapping("/clients/signup")
    public ClientOutputDTO createClientAccount(@RequestBody AuthInputDTO authInputDTO){

        Role role = this.iRoleService.getRoleById(authInputDTO.getRoleId());
        Auth auth = this.modelMapper.map(authInputDTO, Auth.class);
        Client client = new Client();
        client.setActive(true);
        client.setVerified(true);

        //add default client preference and client entity
        Preference preference = this.modelMapper.map(clientPref, Preference.class);
        preference.setClient(client);

        //connection info
        Connection connection = new Connection();
        connection.setAuth(auth);
        connection.setLastConnection(new Date());


        client.setPreference(preference);
        client.setAuth(auth);
        auth.setRole(role);
        auth.setUser(client);
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));
        auth.setConnection(connection);
        role.getAuths().add(auth);


        auth = this.iAuthService.creatAuth(auth);
        return this.modelMapper.map((Client)auth.getUser(), ClientOutputDTO.class);
    }



    @PostMapping("/employees/signup")
    public EmployeeOutputDTO createEmployeeAccount(@RequestBody AuthInputDTO authInputDTO){

        Role role = this.iRoleService.getRoleById(authInputDTO.getRoleId());
        Auth auth = this.modelMapper.map(authInputDTO, Auth.class);
        Employee employee = new Employee();
        employee.setActive(true);
        employee.setVerified(true);

        //connection info
        Connection connection = new Connection();
        connection.setAuth(auth);
        connection.setLastConnection(new Date());


        auth.setUser(employee);
        auth.setRole(role);
        auth.setPassword(this.passwordEncoder.encode(auth.getPassword()));
        auth.setConnection(connection);
        role.getAuths().add(auth);
        employee.setAuth(auth);



        auth = this.iAuthService.creatAuth(auth);
        return this.modelMapper.map((Employee)auth.getUser(), EmployeeOutputDTO.class);
    }



    @GetMapping("/login")
    public AuthOutputDTO login(@RequestParam String email, @RequestParam String password){
        return this.modelMapper.map(this.iAuthService.loadAuthByEmail(email), AuthOutputDTO.class);
    }

    
    @PostMapping("/verified/{email}")
    public AuthOutputDTO verified(@PathVariable String email){
       Auth auth = this.iAuthService.loadAuthByEmail(email);
       auth.getUser().setVerified(true);
       auth = this.iAuthService.updateAuth(auth);
        return this.modelMapper.map(auth, AuthOutputDTO.class);
    }




}
