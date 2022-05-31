package com.example.jwtdemo.service;

import com.example.jwtdemo.entity.Project;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.exceptions.ProjectIdException;
import com.example.jwtdemo.exceptions.ProjectNotFoundException;
import com.example.jwtdemo.repository.ProjectRepository;
import com.example.jwtdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Project saveOrUpdateProject(Project project, String username){
 
        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject !=null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }

        try{
            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    @Override
    public Project findProjectByIdentifier(String projectId,String username){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID : '" + projectId+"' does not exists");
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project Not Found in your account");
        }


        return project;
    }

    @Override
    public Iterable<Project> findAllProject(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public void deleteProjectByIdentifier(String projectId,String username){

        projectRepository.delete(findProjectByIdentifier(projectId,username));
    }
}
