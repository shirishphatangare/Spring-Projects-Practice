import axios from 'axios'

// Axios HTTP Client - Connecting React with RestFul API
// Axios HTTP Client - A Promise based HTTP client for the browser and node.js
// Promise API - Simple asynchronous calls with callback functions .then(), .catch()

class HelloWorldService {

    executeHelloWorldService() {
        //console.log('executed service')
    	// Returning a Promise object 
        return axios.get('http://localhost:8080/hello-world');
    }

    executeHelloWorldBeanService() {
        //console.log('executed service')
    	// Returning a Promise object
        return axios.get('http://localhost:8080/hello-world-bean');
    }

    executeHelloWorldPathVariableService(name) {
        //console.log('executed service')
        // let username = 'in28minutes'
        // let password = 'dummy'

        // let basicAuthHeader = 'Basic ' +  window.btoa(username + ":" + password)
    	// Returning a Promise object
        return axios.get(`http://localhost:8080/hello-world/path-variable/${name}`
            // , 
            //     {
            //         headers : {
            //             authorization: basicAuthHeader
            //         }
            //     }
        );
    }

}

export default new HelloWorldService()