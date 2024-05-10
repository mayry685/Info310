
var calendarInstance;
var modal;
var overlay;
var openModalBtn;
var closeModalBtn;

// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],

    mounted() {
        this.loadCalendar()
    },
    
    data() {
        return {
            isEventModalOpen: false, // Indicates whether the modal is open or closed
            isAssignmentModalOpen: false, // Indicates whether the modal is open or closed
            isAddEventModalOpen: false,
            modalEvent: {},      // Holds the details of the event currently displayed in the modal
            modalAssignment: {},      // Holds the details of the event currently displayed in the modal
            nextCalendarView: "Weekly Calendar",
            eventName: '',
            startDate: '',
            endDate: '',
            eventDescription: '',
            location: ''
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
            
            
            
            // Fetch assignments data from the API
//            const url = '/api/assignments/searchByCourseID';

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
        
        closeModal() {
            overlay.classList.add("hidden");
            this.isEventModalOpen = false
            this.isAssignmentModalOpen = false
            this.isAddEventModalOpen = false
            this.endDate = ''
            this.startDate = ''
            this.location = ''
            this.eventDescription = ''
            this.eventName = ''
        },

        // Method to open the event modal and populate it with event details
        showEventModal(info) {
            // Get the event information
            const event = info.event;

            if (!event.extendedProps.event) {
                this.showAssignmentModal(info);
                return;
            }
            
            // Extract the necessary details from the event object
            const modalEventData = {
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
            }
        },
        
        addEventModal() {
            overlay.classList.remove("hidden");
            this.isAddEventModalOpen = true
        },
        
        addEvent() {
            function formatDate(isoDate) {
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
            }
            
            const eventData = {
                EventName: this.eventName,
                StartDate: formatDate(this.startDate),
                EndDate: formatDate(this.endDate),
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
