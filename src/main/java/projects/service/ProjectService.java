package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectDao;
import projects.entity.Project;
//
//service layer/class is limited in scope, but enables: 
//separation of input/output, biz logic, db reads and writes
//
public class ProjectService {
	private ProjectDao projectDao = new ProjectDao();
	
//use this method to call the DAO class to insert a new project row
//return the project object with new pk value

	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}

//method returns results of call to DAO class
	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}

	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException(
						"Project with project ID=" + projectId 
						+ " does not exist."));
	}
}
