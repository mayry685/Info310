class CourseList {
    constructor(account, course) {
        this.AccountID = account;
        this.CourseID = course;
    }
}

var enrolApi = "/api/courseList";
var getCoursesApi = "/api/courses";
var accountApi = "/api/accounts/searchByUsername";
var getCourseListsApi = "/api/courseList/accountId";
var deleteCourseListApi = "/api/courseList/delete/";

const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    mounted() {
        this.getCourses();
        this.getCourseLists();
        dataStore.commit("error", [])
    },
        data() {
            return {
                signedIn: this.signedInUser!==null,
                courses: new Array(),
                course: new Object(),
                selectedCourse: new Object(),
                courseLists: new Array()
            };
        },
        computed: Vuex.mapState({
            signedInUser: 'signedInUser'
        }),
        methods: {
            enrol(course) {
                var signedInUser = dataStore.state.signedInUser;
                this.selectedCourse = course;
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
                    })
                    .catch(error => {
                        alert("You are already enrolled in this paper.");
                    });
                } else {
                    alert("Invalid Account");
                }
            },
            drop(courseListId) {    
                axios.delete(`/api/courseList/delete/${courseListId}`)
                .then(() => {
                    window.location = "paper-selection.html";
                })
                .catch(error => {
                    console.error(error);
                });

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
            },
            getCourseLists() {
                var signedInUser = dataStore.state.signedInUser;
                axios.get(`/api/courseList/${signedInUser.AccountId}`)
                .then(response => {
                    this.courseLists  = response.data;
                    console.info(this.courseLists);
                })
                .catch(error => {
                    console.error(error);
                });
            },
            isEmptyCourseList(){
                return this.courseLists.length === 0;
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