package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;
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

//methods return results of call to DAO class
	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}

	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException(
						"Project with project ID=" + projectId 
						+ " does not exist."));
	}

//method calls DAO to update project details, pass project object
	public void modifyProjectDetails(Project project) {
//check DAO if update successful, return details to the caller (menu app class)
//if project not found, throw exception
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID="
					+ project.getProjectId() + " does not exist.");
		}
	}
//delete project and its details from db
	public void deleteProject(Integer projectId) {
//call DAO method to confirm deletion
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID=" + projectId + " does not exist.");
		}
	}
}
