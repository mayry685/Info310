
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
            isModalOpen: false, // Indicates whether the modal is open or closed
            modalEvent: {}      // Holds the details of the event currently displayed in the modal
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
                    completed: event.Completed // Whether the event is completed or not
                }));

                // Add the events to the calendar
                calendarInstance.addEventSource(events);
            })
            .catch((error) => {
                console.error('Error fetching events:', error);
            });

            // Render the calendar
            calendarInstance.render();
        },
        
        closeModal() {
            overlay.classList.add("hidden");
            this.isModalOpen = false
        },

        // Method to open the event modal and populate it with event details
        showEventModal(info) {
            // Get the event information
            const event = info.event;
            
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
            this.isModalOpen = true
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
                        break;
                    case 'dayGridWeek':
                        nextView = 'timeGridDay';
                        break;
                    case 'timeGridDay':
                        nextView = 'dayGridMonth';
                        break;
                    default:
                        nextView = 'dayGridMonth';
                        break;
                }

                // Change the calendar view to the next view
                calendarInstance.changeView(nextView);
            }
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
