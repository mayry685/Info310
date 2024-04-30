class CourseList {
    constructor(account, course) {
        this.AccountID = account;
        this.CourseID = course;
    }
}

var enrolApi = "/api/courseList";
var getCoursesApi = "/api/courses";
var accountApi = "/api/accounts/searchByUsername"
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    mounted() {
        this.getCourses();
    },
        data() {
            return {
                signedIn: this.signedInUser!==null,
                courses: new Array(),
                course: new Object(),
                selectedCourse: new Object()
            };
        },
        computed: Vuex.mapState({
            signedInUser: 'signedInUser'
        }),
        methods: {
            enrol(course) {
                var signedInUser = dataStore.state.signedInUser;
                this.selectedCourse = course;
                console.error(this.selectedCourse);
                if (typeof(this.selectedCourse) === "object") {
                    alert("You have not selected a paper.");
                } else if (this.signedInUser!== undefined) {
                    
                    var courseList = new CourseList(
                        this.signedInUser.AccountId,
                        this.selectedCourse);

                    axios.post(enrolApi, courseList, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                            .then(() => {
                                window.location = "paper-selection.html";
                                alert("Paper selection succussful");
                            })
                            .catch(error => {
                                alert("You are already enrolled in this paper.");
                            });
                } else {
                    alert("Invalid Account");
                }
            },
            getCourses() {
                axios.get(getCoursesApi)
                .then(response => {
                    this.courses = response.data;
                })
                .catch(error => {
                    console.error(error);
                    alert("An error occurred - check the console");
                });
            }
        }
});

import { BasicAccessAuthentication } from "./authentication.js";
// import the navigation menu
import { navigationMenu } from "./navigation-menu.js";
app.component('navmenu', navigationMenu);
import { dataStore } from "./data-store.js";
app.use(dataStore);


app.mount("main");