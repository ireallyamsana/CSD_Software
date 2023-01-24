package PE2;


import java.util.*;
/* PLEASE DO NOT MODIFY A SINGLE STATEMENT IN THE TEXT BELOW.
READ THE FOLLOWING CAREFULLY AND FILL IN THE GAPS

I hereby declare that all the work that was required to
solve the following problem including designing the algorithms
and writing the code below, is solely my own and that I received
no help in creating this solution and I have not discussed my solution
with anybody. I affirm that I have read and understood
the Senate Policy on Academic honesty at
https://secretariat-policies.info.yorku.ca/policies/academic-honesty-senate-policy-on/
and I am well aware of the seriousness of the matter and the penalties that I will face as a
result of committing plagiarism in this assignment.

BY FILLING THE GAPS,YOU ARE SIGNING THE ABOVE STATEMENTS.

Full Name: Pramit Kishore Choudhury
Student Number: 219139260
Course Section: Section A, Lab 02
*/


/**
 * The Public Class CSD is used to represent the Computer Science Department of a University.
 * It contains different methods to based on the different works of the faculty members, students, directors etc.
 */
public class CSD {

    //The MAXIMUM VALUE OF DEPARTMENT POSITIONS given in the instruction
    //Chair Person and Program Directors are fixed values. The other positions are based on up to and maximum of the given value.

    protected final int maxChair = 1; //ChairPerson
    protected final int maxPD = 3; //Program Director
    protected final int maxFacultyToPD = 25;    //Faculty assigned to Program Director
    protected final int maxFacultyMembers = 70;
    protected final int maxGradStd = 150;       //Graduate Students
    protected final int maxUnderGradStd = 500;  //UnderGrad Students
    protected final int maxGradTA = 5;          //Graduate Students Working as TA
    protected final int maxUgAdvise = 8;        //Under-Graduate Students assigned to Advising
    //end of maximum position instance variable declaration


    //Creating instance variables for the Positions in the Computer Science Department

    private ChairPerson chairMan;

    private int numChair;
    private int numPD;              //Program Director
    private int numFacultyMembers;  //Faculty Members
    private int numFacultyToPD;     //Number of faculty members assigned to a program director (max 25)
    private int numUnderGradStd;    //Under-Graduate Students
    private int numGradStd;         //Graduate Students
    static int studentNumID = 1000; //Unique student ID starting from 1000
    static int employeeNumID = 100; //Unique Employee ID starting from 100
    // end of instance variable declaration



    // Creating Corresponding ArrayList and Hashset of
    private ArrayList<Faculty> numFacultyMembersList;
    //Faculty members
    private ArrayList<ProgramDirector> numPDList;
    //Program directors
    private HashSet<Student> numUnderGradStdSet;
    //Undergraduate students
    private HashSet<Student> numGradStdSet;
    //Graduate students

    //end of declaring the ArrayList and Hashset




    /**
     * Default Constructor for CSD Class.
     * @param chairMan is the Chairperson for the Computer Science Department
     */
    public CSD(ChairPerson chairMan) {

        numFacultyMembersList = new ArrayList<>();
        numPDList = new ArrayList<>();
        numUnderGradStdSet = new HashSet<>();
        numGradStdSet = new HashSet<>();
        this.chairMan = chairMan;
        numChair = 1;
        numPD = 0;
        numFacultyMembers = 0;
        numFacultyToPD = 0;
        numGradStd = 0;


    }


    /**
     * Getter Method for the Chairperson
     * @return ChairMan is a reference to chairperson
     */
    public ChairPerson getChairPerson() {

        return chairMan;

    }



    /**
     * Setter Method for the new Chairperson
     * @param newChairMan is the new chairperson
     */
    public void setChairPerson(ChairPerson newChairMan) {

        this.chairMan = newChairMan;


    }



    /**
     *Getter Method for number of undergraduate students enrolled at the moment
     * @return an integer the represents the number of undergrad students enrolled at the moment
     */
    public int getNumOfUGradStudents() {

        return numUnderGradStd;


    }



    /**
     * This Method admits an UnderGrad student to the department until the seat limit is reached.
     * @param student is an undergrad student to be admitted
     * @throws NoSpaceException when the maximum limit is reached
     */
    public void AdmitStudent(UGrad student) throws NoSpaceException {

        //While the maximum Limit has not been reached
        if (numUnderGradStd < maxUnderGradStd) {

            if(!numUnderGradStdSet.contains(student)) { //if there is no other student with same details

                numUnderGradStdSet.add(student); //Adding the student
                numUnderGradStd = numUnderGradStdSet.size(); //Updating size of the set

                //Assigning
                for(Faculty faculty: numFacultyMembersList) {

                    if(faculty.getNumOfAdvisingUGrads() < maxUgAdvise) { //if the maximum students assigned to advising limit is not reached

                        faculty.setAdvisee(student);

                        student.advisor = faculty; //assigns the student advisor
                        break;

                    }

                }

            }

        }


        else {


            throw new NoSpaceException("Admit Undergrad Failed");
        }

    } //end of method AdmitStudent



    /**
     * Removes the records of an undergrad student when the student graduates
     * @param student is UGrad or undergrad type.
     */
    public void AlumnusUGrad(UGrad student) {

        numUnderGradStdSet.remove(student); //remove student

        student.getAdvisor().getAdvisingUgrads().remove(student); //remove advisor

        numUnderGradStd = numUnderGradStdSet.size(); //update size


    }//end of method AlumnusUGrad

    /**
     * This method admits a grad student and also assign the grad student as a TA to a faculty who has not exceeded the TAs limit yet.
     * The department cannot admit the same grad student twice. Hence, there is no duplicate.
     * The TAs are assigned to faculty on a first come first serve basis.
     * @param student is a graduate student
     * @throws NoSpaceException when the maximum limit is reached
     */
    public void HireTA(Grad student) throws NoSpaceException {

        //While the maximum Limit has not been reached
        if(numGradStd < maxGradStd) {

            if (!numGradStdSet.contains(student)) { //if there is no other student with same details

                numGradStdSet.add(student); //Adding the student

                numGradStd = numGradStdSet.size(); //Updating size of the set

                //Assigning
                for(Faculty faculty: numFacultyMembersList) {


                    if(faculty.getNumOfTAs() < maxGradTA) {

                        faculty.setTA(student);

                        student.advisor = faculty; //assigning student faculty
                        break;

                    }

                }

            }


        }


        else {

            throw new NoSpaceException("Admit Grad Failed");

        }

    } //end of method HireTA


    /**
     * Removes the records of a grad student when the student graduates
     * @param student is a graduate student about to graduate
     * @throws NoTAException when there isn't a TA from their faculty left
     */
    public void AlumnusGrad(Grad student) throws NoTAException {

        Faculty TA_Advisor;

        TA_Advisor = student.getAdvisor();

        numGradStdSet.remove(student); //removes student
        TA_Advisor.getTAs().remove(student); //remove advisor
        numGradStd = numGradStdSet.size(); //updates size

        if (TA_Advisor.getNumOfTAs() == 0) {
            throw new NoTAException(); //throws NoTAException
        }

    } //end of method AlumnusGrad




    /**
     * Returns sorted list of undergrads
     * @return array list of sorted undergrads
     */
    public ArrayList<UGrad> ExtractAllUGradDetails() {

        ArrayList<UGrad> UGradDetails = new ArrayList<>();
        for(Student student: numUnderGradStdSet) {

            UGradDetails.add((UGrad) student);
        }

        UGradDetails.sort(Student::compareTo);
        return UGradDetails;

    } //end of method ExtractAllUGradDetails()

    /**
     * Returns sorted list of faculty members
     * @return array list of sorted faculty members
     */
    public ArrayList<Faculty> ExtractAllFacultyDetails() {

        ArrayList<Faculty> FacultyDetails = new ArrayList<>();
        for (Faculty faculty: numFacultyMembersList) {

            FacultyDetails.add(faculty);
        }
        FacultyDetails.sort(Faculty::compareTo);
        return FacultyDetails;

    } //end of method  ExtractAllFacultyDetails()


    /**
     * sorts list of graduate students
     * @return array list of sorted graduates
     */
    public ArrayList<Grad> ExtractAllGradDetails() {

        ArrayList<Grad> GradDetails = new ArrayList<>();

        for(Student student: numGradStdSet) {

            GradDetails.add((Grad) student);

        }

        GradDetails.sort(Student::compareTo);
        return GradDetails;

    } //end of ExtractAllGradDetails()




    /**
     * @author Pramit
     * This method hires a faculty member and assigns them to a program director
     * @param faculty is a faculty member to be assigned
     * @throws NoSpaceException when faculty member limit is reached
     */
    public void HireFaculty(Faculty faculty) throws NoSpaceException {

        //While the maximum Limit has not been reached
        if (numFacultyMembers < maxFacultyMembers) {

            //if there is no other faculty with same details
            if (!numFacultyMembersList.contains(faculty)) {

                numFacultyMembersList.add(faculty); //Adding the faculty
                numFacultyMembers = numFacultyMembersList.size(); //Updating size of the list

                for (ProgramDirector programDirector: numPDList) {

                    if (programDirector.getProgram().equals(faculty.getProgram())) {

                        programDirector.facultyAssigned.add(faculty);

                        faculty.programDirector = programDirector;

                    }

                }

            }

        }


        else {

            throw new NoSpaceException("Faculty limit reached!");
        }

    } //end of HireFaculty


    /**
     * This method adds a program director to the department
     * @param programDirector is the program director to be added
     * @throws NoSpaceException when program director limit has been reached
     */
    public void addProgramDirector(ProgramDirector programDir) throws NoSpaceException {

        //While the maximum Limit has not been reached
        if (numPD < maxPD) {

            //if there is no other PD with same details
            if (!numPDList.contains(programDir)) {

                numPDList.add(programDir); //Adding them to the list

                numPD= numPDList.size(); //Updating size of the set
            }

            chairMan.programDirectors.add(programDir); //adds them to the list of program directors the chair overlooks

        }

        else {

            throw new NoSpaceException("Program director limit reached!");
        }

    }

    /**
     * Getter Method for list of faculty members
     * @return ArrayList of faculty members
     */
    public ArrayList<Faculty> getFaculty() {

        return numFacultyMembersList;
    }

    /**
     * Getter Method for total number of faculty members
     * @return int representing number of faculty members
     */
    public int getNumOfFaculty() {

        return numFacultyMembersList.size();
    }

    /**
     * Getter Method for total number of grad students
     * @return int representing number of grad students
     */
    public int getNumOfGradStudents() {

        return numGradStd;
    }



    /**
     * Returns sorted list of advisees under a faculty member
     * @return array list of sorted advisees
     */
    public ArrayList<Student> ExtractAdviseesDetails(Faculty faculty) {


        ArrayList<Student> AdviseeDetails = new ArrayList<>(faculty.getAdvisingUgrads());

        AdviseeDetails.sort(Student::compareTo);
        return AdviseeDetails;

    }////end of ExtractAdviseesDetails

    /**
     * Returns sorted list of TAs under a specific faculty member
     * @param faculty is the faculty member
     * @return sorted array list of TAs under <code>faculty</code>
     */
    public static ArrayList<Grad> ExtractTAsDetails(Faculty faculty) {

        ArrayList<Grad> TAsDetails = new ArrayList<>();
        for (Grad TA: faculty.getTAs()) {

            TAsDetails.add(TA);

        }


        TAsDetails.sort(Grad::compareTo);

        return TAsDetails;

    } //end of method ExtractTAsDetails







    /**
     *
     * @author Pramit
     * Abstract class Person is for storing the basic details of a person according to the instructions.
     * The attributes have been mentioned an explained in the comments
     */
    abstract class Person {

        protected String firstName;    //Type: String. Stores the First Name of a person.
        protected String lastName;     //Type: String. Stores the Last Name of a person.
        protected int age;             //Type: int. Stores the age of a person.
        protected String gender;       //Type: String. Stores the gender of a person.
        protected String address;      //Type: String. Stores the address of a person.


        /*
         * Note while writing the code
         * I have declared this class as an Abstract class because I don't want to create objects of this class.
         * It would seem weird to create object out of the Person class because the attributes here are too generic.
         * There will always be the question of what type of Person they are, based on how they are involved in the Computer Science Department.
         * For this project, it is more important to specify the type of Person according to how they are involved in the Computer Science Department.
         */


    } //end of abstract Class Person



    /**
     *
     * @author Pramit
     * Class Academics represents an employee of the department. It extends Abstract class Person.
     * The attributes have been mentioned an explained in the comments
     */
    class Academics extends Person {

        //All the basic info such as first name, last name are obtained from the Person class.

        protected int employeeID; //Type: int. Stores employee ID number
        protected String program; //Type: String. Stores the name of the program they are working at
        private double salary;    //Type double. Stores their salary


        /**
         * Default Constructor for Academics Class. Sets the attributes to default values.
         */
        public Academics() {

            this.firstName = null;
            this.lastName = null;
            this.age = 0;
            this.gender = null;
            this.address = null;

        }

        /**
         * Overloaded constructor for Academics Class
         * @param firstName Type: String. Stores the First Name of an employee
         * @param lastName  Type: String. Stores the Last Name of an employee
         * @param age       Type: int. Stores the age of an employee
         * @param gender    Type: String. Stores the gender of an employee
         * @param address   Type: String. Stores the address of an employee
         */
        public Academics(String firstName, String lastName, int age, String gender, String address) {


            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
            this.address = address;
            this.employeeID = CSD.employeeNumID;
            CSD.employeeNumID += 1;


        }


        /**
         * Getter Method for employee ID number
         * @return an int representing the employee ID Number
         */
        public int getEmployeeID() {

            return employeeID;

        }

        /**
         * Getter Method for program they are working in
         * @return a String representing the program of the academic
         */
        public String getProgram() {

            return program;

        }


        /**
         * Setter Method for program
         * @param program a String representing the program of the academic
         */
        public void setProgram(String program) {

            this.program = program;

        }



        /**
         * Getter Method for salary
         * @return A Double representing the salary of the academic
         */
        public double getSalary() {


            return salary;

        }



        /**
         * Setter Method for salary of employees
         * @param salary the salary to set
         */
        public void setSalary(double salary) {

            this.salary = salary;


        }


        /**
         * This method compares two academics evaluating their ID number, salary, and program
         * @param o is an Object to be casted as an academic
         * @return true if both academics are equal, false if not equal
         */
        @Override
        public boolean equals(Object o) {

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Academics academics = (Academics) o;
            return employeeID == academics.employeeID && Double.compare(academics.salary, salary) == 0 && program.equals(academics.program);


        }



        /**
         * Generates a unique hashcode for an academic
         * @return int representing a unique hashcode
         */
        @Override
        public int hashCode() {

            return Objects.hash(employeeID, program, salary);

        }


        /**
         * Override of toString that returns the details of the academic
         * @return String representing academic's details
         */
        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + " [" + getEmployeeID() + ", " + getSalary() + "[[" +
                    firstName + ", " + lastName + ", " + age + ", " + gender + ", " + address + "]]]";
        }
    }

    /**
     *
     * @author Pramit
     * Class Administrator is for the employees in Admin Position. It extends the Class Academics
     *
     */
    abstract class Administrator extends Academics
    {
        /**
         * Constructor for Administrator
         */
        public Administrator()
        {
            super();
        }

        /**
         * Overloaded constructor for Administrator Class.
         * @param firstName Type: String. Stores the First Name of an employee
         * @param lastName  Type: String. Stores the Last Name of an employee
         * @param age       Type: int. Stores the age of an employee
         * @param gender    Type: String. Stores the gender of an employee
         * @param address   Type: String. Stores the address of an employee
         */
        public Administrator(String firstName, String lastName, int age, String gender, String address) {

            super(firstName, lastName, age, gender, address);

        }

    }



    /**
     * Class ProgramDirector a program director overlooks and manages only faculty members in the same program.
     * It Extends Class Administrator
     */
    class ProgramDirector extends Administrator {

        protected ArrayList<Faculty> facultyAssigned;

        /**
         * Constructor for ProgramDirector
         */
        public ProgramDirector() {

            super();

        }

        /**
         /**
         * Overloaded constructor for ProgramDirector (PD) Class.
         * @param firstName Type: String. Stores the First Name of a PD
         * @param lastName  Type: String. Stores the Last Name of a PD
         * @param age       Type: int. Stores the age of a PD
         * @param gender    Type: String. Stores the gender of a PD
         * @param address   Type: String. Stores the address of a PD
         */
        public ProgramDirector(String firstName, String lastName, int age, String gender, String address) {


            super(firstName, lastName, age, gender, address);

            facultyAssigned = new ArrayList<>();


        }


        /**
         * Getter Method for the number of faculty members assigned to this program director
         * @return int representing the number of assigned faculty members
         */
        public int getNumOfAssignedFaculty() {

            return facultyAssigned.size();

        }


    }



    /**
     *
     * @author Pramit
     * There is only one Chairperson for the department. The Chairperson overlooks and manages all the Program Directors.
     * Class ChairPerson Extends Class Adminstrator
     */
    class ChairPerson extends Administrator {


        ArrayList<ProgramDirector> programDirectors;

        /**
         * Constructor for ChairPerson
         * @param firstName String -- representing the first name of the chair person
         * @param lastName String -- representing the last name of the chair person
         * @param age int -- representing the age of the chair person
         * @param gender String -- representing the gender of the chair person
         * @param address String -- representing the address of the chair person
         */
        public ChairPerson(String firstName, String lastName, int age, String gender, String address) {

            super(firstName, lastName, age, gender, address);

            programDirectors = new ArrayList<>();
        }


        /**
         * Override of toString method that prints the details of the chair person
         * @return String with details of the ChairPerson
         */
        @Override
        public String toString() {

            return "Chair Person" + " [[[" + getEmployeeID() + ", " + getSalary() + "[" + firstName + ", " + lastName + ", " + age + ", " + gender + ", " + address + "]]]]";
        }


    }



    /**
     * @author Pramit
     * The Class is for Faculty. It also supervises TAs.
     * A Faculty can have several undergrad Students assigned at the same time.
     *
     */
    class Faculty extends Academics implements Comparable {

        protected ArrayList<UGrad> advisees;
        protected ArrayList<Grad> TAs;
        protected ProgramDirector programDirector;

        /**
         * Constructor for faculty
         * @param firstName String -- representing first name of the faculty
         * @param lastName String -- representing the last name of the faculty
         * @param age int -- representing the age of the faculty
         * @param gender String -- representing the gender of the faculty
         * @param address String -- representing the address of the faculty
         */
        public Faculty(String firstName, String lastName, int age, String gender, String address) {

            super(firstName, lastName, age, gender, address);
            advisees = new ArrayList<>();
            TAs = new ArrayList<>();
        }

        /**
         * Getter Method for the program of the faculty
         * @return String that gets the program of the faculty
         */
        public String getProgram() {

            return program;
        }

        /**
         * Setter Method for the program of the faculty
         * @param program a String representing the program of the faculty
         */
        public void setProgram(String program) {

            this.program = program;
        }

        /**
         * Getter for the list of undergrads under this faculty
         * @return an array list of advisees
         */
        public ArrayList<UGrad> getAdvisingUgrads() {

            return advisees;
        }

        /**
         * Getter for the list of TAs under this faculty
         * @return an array list of TAs
         */
        public ArrayList<Grad> getTAs() {

            return TAs;
        }

        /**
         * Set <code>student</code> as a TA
         * @param student as a graduate student
         */
        public void setTA(Grad student) {

            TAs.add(student);
        }

        /**
         * Getter for the number of TAs the faculty member supervises
         * @return int representing the number of TAs
         */
        public int getNumOfTAs() {

            return TAs.size();
        }

        /**
         * Getter for the number of advisees the faculty member has
         * @return int representing the number of advisees
         */
        public int getNumOfAdvisingUGrads() {

            return advisees.size();
        }

        /**
         * Assigns an undergraduate to the faculty member
         * @param undergrad is the undergrad to assign as an advisee
         */
        public void setAdvisee(UGrad undergrad) {

            advisees.add(undergrad);
        }


        /**
         * Override of compareTo. Compares faculty members lexicographically.
         * @param facultyToCompare is the other faculty member to compare
         * @return an int: -1 if faculty < facultyToCompare; 0 if faculty = facultyToCompare; 1 if faculty > facultyToCompare
         */
        @Override
        public int compareTo(Object facultyToCompare) {

            //compare names lexicographically
            Faculty faculty = (Faculty) facultyToCompare;
            String thisFacultyName = this.firstName + ", " + this.lastName;
            String otherFacultyName = faculty.firstName + ", " + faculty.lastName;
            return thisFacultyName.compareTo(otherFacultyName);
        }

        /**
         * Override of toString. Returns a String with the details of the faculty member.
         * @return a String with faculty member's details
         */
        @Override
        public String toString() {

            return "Faculty " + getProgram() + "[[" + getEmployeeID() + ", " + getSalary() + "[" + firstName + ", " + lastName + ", " + age + ", " + gender + ", " + address + "]]]";
        }

    } //end



    /**
     * This class is for making a Student, where we introduce two new attributes, studentID and assigned advisor.
     */
    class Student extends Person implements Comparable {

        //All the basic info such as first name, last name are obtained from the Person class.

        protected int studentID;
        protected Faculty advisor;


        /**
         * Constructor for Student.
         * @param firstName Type: String. Stores First Name of the Student
         * @param lastName  Type: String. Stores Last Name of the Student
         * @param age       Type: int     Stores the age of the Student
         * @param gender    Type: String. Stores Gender of the Student
         * @param address   Type: String. Stores Address the Student
         */
        public Student(String firstName, String lastName, int age, String gender, String address) {

            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
            this.address = address;
            this.studentID = CSD.studentNumID;
            CSD.studentNumID += 1;
        }

        /**
         * Getter method for student ID.
         * @return an int representing the student ID
         */
        public int getStudentID() {

            return studentID;
        }

        /**
         * Getter method for advisor.
         * @return a faculty member assigned to the undergraduate student
         */
        public Faculty getAdvisor() {

            return advisor;
        }


        /**
         * Override of compareTo. Compares faculty members lexicographically.
         * @param studentToCompare is the other faculty member to compare
         * @return an int: -1 if student < studentToCompare; 0 if student = studentToCompare; 1 if student > studentToCompare
         */
        @Override
        public int compareTo(Object studentToCompare) {

            //compare names lexicographically
            Student student = (Student) studentToCompare;
            String thisStudentName = this.firstName + ", " + this.lastName;
            String otherStudentName = student.firstName + ", " + student.lastName;

            return thisStudentName.compareTo(otherStudentName);

        }




        /**
         * Override of hashcode(). Generates a unique hash code based on the student's ID
         * @return an int representing the hashcode of the student
         */
        @Override
        public int hashCode() {

            return Objects.hash(studentID);

        }



    }



    /**
     * Class UnderGradStd represents Under-Graduate Students
     */
    class UGrad extends Student {

        //All the basic details are obtained from the Abstract Class Student


        /**
         *
         * Constructor for UGrad
         * @param firstName Type: String. Stores First Name of the Student
         * @param lastName  Type: String. Stores Last Name of the Student
         * @param age       Type: int     Stores the age of the Student
         * @param gender    Type: String. Stores Gender of the Student
         * @param address   Type: String. Stores Address the Student
         */
        public UGrad(String firstName, String lastName, int age, String gender, String address) {

            super(firstName, lastName, age, gender, address);

        }



        /**
         * Override of toString.
         * @return a String with student's details
         */
        @Override
        public String toString() {

            return "Undergraduate" + " [" + getStudentID() + "[[" + firstName + ", " + lastName + ", " + age + ", " + gender + ", " + address + "]]]";
        }

    } // end of Class UGrad


    /**
     * @author Pramit
     * Class GradStd represents a graduate student.
     */
    class Grad extends Student {



        //All the basic details are obtained from the Abstract Class Student




        /**
         *
         * Constructor for Grad
         * @param firstName Type: String. Stores First Name of the Student
         * @param lastName  Type: String. Stores Last Name of the Student
         * @param age       Type: int     Stores the age of the Student
         * @param gender    Type: String. Stores Gender of the Student
         * @param address   Type: String. Stores Address the Student
         */
        public Grad(String firstName, String lastName, int age, String gender, String address) {

            super(firstName, lastName, age, gender, address);

        }



        /**
         * Override of toString.
         * @return a String with student's details
         */
        @Override
        public String toString() {

            return "Graduate" + " [" + getStudentID() + "[[" + firstName + ", " + lastName + ", " + age + ", " + gender + ", " + address + "]]]";
        }

    } // end of class Grad






// _______________________________________________          EXCEPTIONS            _____________________________________________________________


    /**
     *
     * @author Pramit
     * NoSpaceException is thrown when : if the university reaches the maximum quota for students (i.e., you  cannot admit any new student)
     * Based on Case 1
     */
    class NoSpaceException extends Exception {

        /**
         * Constructor for NoSpaceException.
         */
        public NoSpaceException() {

            super(); //calls the superior class

        }


        /**
         * Overloaded constructor for NoSpecialtyException
         * @param message is a message to show the user
         */
        public NoSpaceException(String message) {
            super(message); //calls the superior class
        }

    } // end of Class NoSpaceException



    /**
     *
     * @author Pramit
     * NoSpecialtyException is  if a faculty member is retiring from the university, but no other faculty is available with the same specialty of the program.
     * Based on Case 2
     */


    class NoSpecialtyException extends Exception {


        /**
         * Constructor for NoSpecialtyException.
         */
        public NoSpecialtyException() {


            super(); //calls the superior class

        }


        /**
         * Overloaded constructor for NoSpecialtyException
         * @param message is a message to show the user
         */
        public NoSpecialtyException(String message) {

            super(message); //calls the superior class

        }

    }

    /**
     * NoTAException is thrown  if a TA is graduating from the department, but no other TA is available under supervision/work with a particular faculty
     * Based on Case 3.
     */
    class NoTAException extends Exception {


        /**
         *
         * Constructor for NoTAException.
         */
        public NoTAException() {



            super(); //calls the superior class

        }

        /**
         * Overloaded constructor for NoTAException.
         * @param message is a message to show the user
         */
        public NoTAException(String message) {

            super(message); //calls the superior class

        }

    } // end of class NoTAException



