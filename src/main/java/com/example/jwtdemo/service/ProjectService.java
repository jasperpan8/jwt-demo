package com.example.jwtdemo.service;

import com.example.jwtdemo.entity.Project;

public interface ProjectService {
    Project saveOrUpdateProject(Project project, String username);
    Project findProjectByIdentifier(String projectId,String username);
    Iterable<Project> findAllProject(String username);
    void deleteProjectByIdentifier(String projectId,String username);
}
