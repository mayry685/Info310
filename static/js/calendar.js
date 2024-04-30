/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


var calendarInstance;

// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],

    mounted() {
        this.loadCalendar()
    },

    methods: {
        loadCalendar() {
            // Get the calendar element
            var calendarEl = document.getElementById('calendar');

            // Create a new FullCalendar instance
            calendarInstance = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth' // Set the initial view of the calendar
            });

            // Fetch events data from the API
            const url = '/api/events/account';

            // Sending the GET request with the parameter
            axios.get(url, {
              params: {
                AccountID: dataStore.state.signedInUser.AccountId
              }
            })
                .then(function (response) {
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
                .catch(function (error) {
                    console.error('Error fetching events:', error);
                });

            // Render the calendar
            calendarInstance.render();
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
