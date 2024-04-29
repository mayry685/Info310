class CourseList {
    constructor(account, course) {
        this.accountId = account;
        this.courseId = course;
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
                signedIn: this.account!==null,
                courses: new Array(),
                course: new Object()
            };
        },
        computed: Vuex.mapState({
            account: 'account'
        }),
        methods: {
            enrol() {
                if (this.course !== null && this.account!== undefined) {
                    

                    var courseList = new CourseList(
                        this.account.AccountId,
                        this.course.CourseId);
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
                                alert("Please select a paper");
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