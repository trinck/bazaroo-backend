package org.mts.usersservice.web;


import org.modelmapper.ModelMapper;
import org.mts.usersservice.dtos.*;
import org.mts.usersservice.entities.Client;
import org.mts.usersservice.entities.Employee;
import org.mts.usersservice.entities.User;
import org.mts.usersservice.services.IClientService;
import org.mts.usersservice.services.IEmployeeService;
import org.mts.usersservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IClientService iClientService;


    @PostMapping("/clients/save")
    public ClientOutputDTO createUser(@RequestBody ClientInputDTO dto){
        Client user = this.modelMapper.map(dto, Client.class);
        return this.modelMapper.map(this.userService.creatUser(user),ClientOutputDTO.class);
    }


    @PostMapping("/employees/save")
    public EmployeeOutputDTO createEmployee(@RequestBody EmployeeInputDTO dto){
        Employee employee = this.modelMapper.map(dto, Employee.class);
        return this.modelMapper.map(this.employeeService.creatEmployee(employee),EmployeeOutputDTO.class);
    }


    @GetMapping("/employees")
    public List<EmployeeOutputDTO> getEmployees(){
        return this.employeeService.getListEmployee().stream().map(emp->this.modelMapper.map(emp,EmployeeOutputDTO.class )).toList();
    }


    @GetMapping("/clients")
    public List<ClientOutputDTO> getClients(){
        return this.iClientService.getListClients().stream().map(emp->this.modelMapper.map(emp,ClientOutputDTO.class )).toList();
    }


    @PostMapping("/{id}/delete")
    public UserOutputDTO deleteAccount(@PathVariable String id){
      return  this.modelMapper.map(this.userService.deleteUserById(id), UserOutputDTO.class);
    }


    @PutMapping("/{id}/profile/{url}")
    public ResponseEntity<String> profileUpdate(@PathVariable String id,@PathVariable String url ){
       this.userService.updateProfile(id, url);
       return new ResponseEntity<>(url,HttpStatus.OK);
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<UserOutputDTO> activeUpdate(@PathVariable String id, @RequestParam Boolean active){

        User user = this.userService.getUserById(id);
        user.setActive(active);
        UserOutputDTO dto = this.modelMapper.map(this.userService.updateUser(user), UserOutputDTO.class);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/employees/{id}/update")
    public EmployeeOutputDTO updateEmployee(@RequestBody EmployeeInputDTO employeeInputDTO){
        Employee employee = this.modelMapper.map(employeeInputDTO, Employee.class);
        return this.modelMapper.map(this.employeeService.updateEmployee(employee),EmployeeOutputDTO.class );
    }



    @PutMapping("/clients/{id}/update")
    public ClientOutputDTO updateClient(@RequestBody ClientInputDTO clientInputDTO){
        Client client = this.modelMapper.map(clientInputDTO, Client.class);
        return this.modelMapper.map(this.iClientService.updateClient(client),ClientOutputDTO.class );
    }

    @GetMapping("/clients/{id}")
    public ClientOutputDTO getClient(@PathVariable String id){
        Client client = this.iClientService.getClientById(id);
        System.out.println(client.getAuth().getConnection());
        return this.modelMapper.map(client, ClientOutputDTO.class);
    }

    @GetMapping("/employees/{id}")
    public EmployeeOutputDTO getEmployee(@PathVariable String id){
        return this.modelMapper.map(this.employeeService.getEmployeeById(id), EmployeeOutputDTO.class);
    }


    @GetMapping("/")
    public List<UserOutputDTO> getUsers(){
        return this.userService.getListUsers().stream().map(emp->this.modelMapper.map(emp,UserOutputDTO.class )).toList();
    }

}
