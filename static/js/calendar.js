
var calendarInstance;
var modal;
var overlay;
var openModalBtn;
var closeModalBtn;

// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],

    mounted() {
        this.loadCalendar(),

        this.loadCourses()

        this.loadToggleButton()

    },
    
    data() {
        return {
            isEventModalOpen: false, // Indicates whether the modal is open or closed
            isAssignmentModalOpen: false, // Indicates whether the modal is open or closed
            isAddEventModalOpen: false,
            isAddAssignmentModalOpen: false,
            modalEvent: {},      // Holds the details of the event currently displayed in the modal
            modalAssignment: {},      // Holds the details of the event currently displayed in the modal
            courses: {},
            nextCalendarView: "Weekly Calendar",
            eventName: '',
            startDate: '',
            endDate: '',
            eventDescription: '',
            location: '',
            assignmentName: '',
            assignmentDescription: '',
            dueDate: '',
            assignmentWeight: 0,
            selectedCourse: '',
            button: null

        };
    },

    methods: {
        loadCalendar() {
            // Get the calendar element
            var calendarEl = document.getElementById('calendar');
            modal = document.querySelector(".modal");
            overlay = document.querySelector(".overlay");
            openModalBtn = document.querySelector(".btn-open");
            closeModalBtn = document.querySelector(".btn-close");

            // Create a new FullCalendar instance
            calendarInstance = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth', // Set the initial view of the calendar
                eventClick: this.showEventModal // Call showEventModal when an event is clicked
            });

            // Fetch events data from the API
            const url = '/api/events/account';

            // Sending the GET request with the parameter
            axios.get(url, {
                params: {
                    AccountID: dataStore.state.signedInUser.AccountId
                }
            })
            .then((response) => {
                // Assuming the API response contains events data in a suitable format
                var events = response.data.map(event => ({
                    id: event.EventID, // Unique identifier for the event
                    title: event.EventName, // Title of the event
                    start: new Date(event.StartDate), // Start date of the event
                    end: new Date(event.EndDate), // End date of the event
                    description: event.EventDescription, // Description of the event
                    location: event.Location, // Location of the event
                    completed: event.Completed, // Whether the event is completed or not
                    color: 'blue',
                    event: true
                }));

                // Add the events to the calendar
                calendarInstance.addEventSource(events);
            })
            .catch((error) => {
                console.error('Error fetching events:', error);
            });

            // Render the calendar
            calendarInstance.render();
            
            // Sending the GET request to fetch assignments
            axios.get("/api/assignments/searchByAccountID", {
                params: {
                    AccountID: dataStore.state.signedInUser.AccountId
                }
            })
            .then((response) => {
                // Assuming the API response contains assignments data in a suitable format
                var assignments = response.data.map(assignment => ({
                    id: assignment.AssignmentID, // Unique identifier for the assignment
                    title: assignment.AssignmentName, // Title of the assignment
                    start: new Date(assignment.DueDate), // Due date of the assignment
                    end: new Date(new Date(assignment.DueDate).setHours(23, 59, 59)), // End is same as start, this is just for the calendar to know when to end
                    description: assignment.AssignmentDescription, // Description of the assignment
                    weight: assignment.Weight, // Weight of the assignment
                    coursename: assignment.CourseName,
                    color: 'green',
                    event: false
                }));

                // Add the assignments to the calendar
                calendarInstance.addEventSource(assignments);
            })
            .catch((error) => {
                console.error('Error fetching assignments:', error);
            });
        },
        
        async loadCourses() {
            try {
                const response = await axios.get(`/api/courseList/${this.$store.state.signedInUser.AccountId}`);
                this.courses = response.data;
            } catch (error) {
                console.error('Error loading courses:', error);
            }
        },
        
        closeModal() {
            overlay.classList.add("hidden");
            this.isEventModalOpen = false
            this.isAssignmentModalOpen = false
            this.isAddEventModalOpen = false
            this.isAddAssignmentModalOpen = false
            this.endDate = ''
            this.startDate = ''
            this.location = ''
            this.eventDescription = ''
            this.eventName = ''
            var dialog = document.querySelector("#eventModalDialog");
            var assignmentDialog = document.querySelector("#AssignmentModalDialog");
            dialog.close();
            assignmentDialog.close();
            addAssignmentDialog.close();
            addEventDialog.close();

        },

        // Method to open the event modal and populate it with event details
        showEventModal(info) {

           var dialog = document.querySelector("#eventModalDialog");
           dialog.showModal();
    

            // Get the event information
            const event = info.event;

            if (!event.extendedProps.event) {
                this.showAssignmentModal(info);
                return;
            }
            
            // Extract the necessary details from the event object
            const modalEventData = {
                id: event.id,
                title: event.title,
                start: event.start,
                end: event.end,
                description: event.extendedProps.description,
                location: event.extendedProps.location,
                completed: event.extendedProps.completed
            };

            // Assign the modalEventData to modalEvent data property
            this.modalEvent = modalEventData;
            
            overlay.classList.remove("hidden");
            this.isEventModalOpen = true


        },

        showAssignmentModal(info) {
            // Get the event information
            const event = info.event;
            
            // Extract the necessary details from the event object
            const modalAssignmentData = {
                id: event.id,
                title: event.title,
                dueDate: event.start,
                description: event.extendedProps.description,
                weight: event.extendedProps.weight,
                courseName: event.extendedProps.coursename
            };

            // Assign the modalEventData to modalEvent data property
            this.modalAssignment = modalAssignmentData;
            
            overlay.classList.remove("hidden");
            this.isAssignmentModalOpen = true
            var assignmentDialog = document.querySelector("#AssignmentModalDialog");
           assignmentDialog.showModal();
        },

        toggleView() {
            console.log("toggle view");
            if (calendarInstance) {
                // Get the current view type
                var currentView = calendarInstance.view.type;

                // Determine the next view type based on the current view
                var nextView = '';
                switch (currentView) {
                    case 'dayGridMonth':
                        nextView = 'dayGridWeek';
                        this.nextCalendarView = "Daily Calendar";
                        break;
                    case 'dayGridWeek':
                        nextView = 'timeGridDay';
                        this.nextCalendarView = "Monthly Calendar";
                        break;
                    case 'timeGridDay':
                        nextView = 'dayGridMonth';
                        this.nextCalendarView = "Weekly Calendar";
                        break;
                    default:
                        nextView = 'dayGridMonth';
                        this.nextCalendarView = "Weekly Calendar";
                        break;
                }

                // Change the calendar view to the next view
                calendarInstance.changeView(nextView);
                this.button.textContent = this.nextCalendarView;
            }
        },

        loadToggleButton() {
            // Troup of buttons we want to insert into
            const toolbarChunk = document.querySelector('.fc-toolbar-chunk:nth-child(3)');
            this.button = document.createElement('button');
            // To style it correctly
            this.button.setAttribute('class', 'fc-button fc-button-primary');
            this.button.textContent = this.nextCalendarView;
            this.button.addEventListener('click', this.toggleView);

            // Insert to the front
            if (toolbarChunk.firstChild) {
                toolbarChunk.insertBefore(this.button, toolbarChunk.firstChild);
            } else {
                toolbarChunk.appendChild(this.button);
            }
        },

    
        
        addEventModal() {
            overlay.classList.remove("hidden");
            this.isAddEventModalOpen = true
            var addEventDialog = document.querySelector("#addEventModalDialog");
            addEventDialog.showModal();
        },
        
        addAssignmentModal() {
            overlay.classList.remove("hidden");
            this.isAddAssignmentModalOpen = true
            var addAssignmentDialog = document.querySelector("#addAssignmentModalDialog");
            addAssignmentDialog.showModal();
        },
        
        formatDate(isoDate) {
            const date = new Date(isoDate);
            const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
            const month = months[date.getMonth()];
            const day = date.getDate();
            const year = date.getFullYear();
            let hour = date.getHours();
            let minute = date.getMinutes();
            let second = date.getSeconds();
            const ampm = hour >= 12 ? 'PM' : 'AM';

            // Convert hour from 24-hour format to 12-hour format
            hour = hour % 12;
            hour = hour ? hour : 12; // Handle midnight (0:00) as 12 AM

            // Add leading zero if minute or second is less than 10
            minute = minute < 10 ? '0' + minute : minute;
            second = second < 10 ? '0' + second : second;

            const formattedDate = `${month} ${day}, ${year}, ${hour}:${minute}:${second} ${ampm}`;
            return formattedDate;
        },
        
        addEvent() {
            
            const eventData = {
                EventName: this.eventName,
                StartDate: this.formatDate(this.startDate),
                EndDate: this.formatDate(this.endDate),
                EventDescription: this.eventDescription,
                Location: this.location,
                AccountID: dataStore.state.signedInUser.AccountId
                // Add more properties as needed
            };
            
            if (this.endDate < this.startDate) {
                dataStore.commit("error", ["End date cannot be before start date\n"])
                return
            } else {
                dataStore.commit("error", "")
            }
            
            // Send eventData to the backend API using Axios
            axios.post('/api/events/CreateEvent', eventData)
                .then(response => {
                    this.loadCalendar()
                })
                .catch(error => {
                    console.error('Error adding event:', error);
                });
                
            this.closeModal()
        },
        

        addAssignment() {
            
            const assignmentData = {
                AssignmentName: this.assignmentName,
                DueDate: this.formatDate(this.dueDate),
                AssignmentDescription: this.assignmentDescription,
                Weight: this.assignmentWeight,
                CourseID: this.selectedCourse
            };
            

            
            // Send eventData to the backend API using Axios
            axios.post('/api/assignments/CreateAssignment', assignmentData)
                .then(response => {
                    this.loadCalendar()
                })
                .catch(error => {
                    console.error('Error adding assignment:', error);
                });
            this.closeModal()
        },
                

        deleteEvent() {
            axios.delete('/api/events/deleteEventByID', {
                params: {
                    EventID: this.modalEvent.id
                }
            })  
            .then(response => {
                console.log('Event deleted successfully:', response.data);
                this.loadCalendar()
            })
            .catch(function (error) {
                console.error('There was an error deleting the event:', error);
            });
            this.closeModal()
        },
        
        deleteAssignment() {
            axios.delete('/api/assignments/DeleteAssignmentByID', {
                params: {
                    AssignmentID: this.modalAssignment.id
                }
            })  
            .then(response => {
                console.log('Assignment deleted successfully:', response.data);
                this.loadCalendar()
            })
            .catch(function (error) {
                console.error('There was an error deleting the assignment:', error);
            });

            this.closeModal()
        }
    }
});

import { BasicAccessAuthentication } from './authentication.js';
// import the navigation menu
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js';
app.use(dataStore);
// attach the controller to the <main> tag
app.mount("main");
