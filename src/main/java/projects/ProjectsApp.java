 package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
//these are the app menu selections	
	// @formatter:off
	private List<String> operations = List.of(
					"1) Add a project", 
					"2) List projects", 
					"3) Select a project",
					"4) Update project details", 
					"5) Delete a project"
					
					);		 
	// @formatter:on
//
//scanner looks for user input in console
//
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();

	public static void main(String[] args) {
		new ProjectsApp().processUserSelection();
	}

//
//switch statements make method calls for user functions
//
	private void processUserSelection() {
		boolean done = false;
		while (!done) {
			try {
				int selection = getUserSelection();

				switch (selection) {
				case -1:
					done = exitMenu();
					break;

				case 1:
					createProject();
					break;

				case 2:
					listProjects();
					break;

				case 3:
					selectProject();
					break;

				case 4:
					updateProjectDetails();
					break;
					
				case 5:
					deleteProject();
					break;

				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
				}
			} catch (Exception e) {
				System.out.println("\nError: " + e.toString());
			}
		}
	}

	private int getUserSelection() {
		printOperations();

		Integer input = getIntInput("Enter a menu selection");

		return Objects.isNull(input) ? -1 : input;
	}

	private Project curProject;

	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");

		operations.forEach(line -> System.out.println("  " + line));

		if (curProject == null) {
			System.out.println("\nYou are not working with a project.");
		}

		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
	}

	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {
			return null;
		}

		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number. Try again.");
		}
	}

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();

		return input.isBlank() ? null : input.trim();
	}

//
//TROUBLESHOOT: had string in place of prompt as parameter in the method declaration	
//private BigDecimal getDecimalInput(String string) { 
//
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {

			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valud decimal number.Try again.");
		}
	}

	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");

		Project project = new Project();

		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);

		Project dbProject = projectService.addProject(project);

		System.out.println("You have successfully created project: " + dbProject);
	}

	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();

		System.out.println("\nProjects:");

		projects.forEach(
				project -> System.out.println("  " + project.getProjectId() + ": " + project.getProjectName()));
	}

	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");

//un-select current project
		curProject = null;

//throw exception if invalid project id entered
		curProject = projectService.fetchProjectById(projectId);
	}
	
//implement the updateProject Details method 
//this method first checks if a project is selected
	private void updateProjectDetails() {
		if(Objects.isNull(curProject)) {
			System.out.println("\nPlease select a project.");
			return;
		}
//get user input for project fields, displaying any curProject values
		String projectName = getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours [" + curProject.getActualHours() + "]");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5) [" + curProject.getDifficulty() + "]");
		String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");

//create new project object with updated values
		Project project = new Project();

//add updated values to project object, else use existing values
		project.setProjectName(Objects.isNull(projectName) ? 
				curProject.getProjectName() : projectName);
		project.setEstimatedHours(Objects.isNull(estimatedHours) ? 
				curProject.getEstimatedHours() : estimatedHours);
	    project.setActualHours(Objects.isNull(actualHours) ? 
	    		curProject.getActualHours() : actualHours);
	    project.setDifficulty(Objects.isNull(difficulty) ? 
	    		curProject.getDifficulty() : difficulty);
	    project.setNotes(Objects.isNull(notes) 
	    		? curProject.getNotes() : notes);

//set projectID from the current project
	    project.setProjectId(curProject.getProjectId());
	    
//call project service to make modifications
	    projectService.modifyProjectDetails(project);
	    
//re-read current project to show updated details
	    curProject = projectService.fetchProjectById(curProject.getProjectId());
	    System.out.println("\nProject details have been updated.");
	}
	
//implement delete project method 
	private void deleteProject() {
		listProjects();
		
//get project id to delete
		Integer projectId = getIntInput("Enter the ID of the project to delete");
		
//exit if no id given
		if(Objects.isNull(projectId)) {
			return;
		}

//call service to delete the project
		projectService.deleteProject(projectId);
		
		System.out.println("\nProject " + projectId + " was deleted successfully.");
		
//if deleted proj was cur proj then reset cur proj
		if(Objects.nonNull(curProject) && projectId.equals(curProject.getProjectId())) {
			curProject = null;
		}
}

	private boolean exitMenu() {
		System.out.println("Exiting the application.");
		return true;
	}

}