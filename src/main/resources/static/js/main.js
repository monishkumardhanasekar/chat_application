// const baseURL = 'http://localhost:8080/api/groups';
// let stompClient = null;
// let currentGroupName = '';  // Track the current group the user is in

// function connect() {
//     const socket = new SockJS('/ws-chat');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//         console.log('Connected: ' + frame);
//     });
// }

// window.onload = connect;

// // WebSocket function to subscribe to a specific group and listen for messages
// function subscribeToGroup(groupName) {
//     if (currentGroupName === groupName) return;  // Prevent subscribing multiple times

//     stompClient.subscribe(`/topic/${groupName}`, function (messageOutput) {
//         showMessage(JSON.parse(messageOutput.body));
//     });
//     currentGroupName = groupName;
// }

// // Function to create a group
// function createGroup() {
//     const groupName = document.getElementById("createGroupName").value;
//     axios.post(`${baseURL}/create`, null, { params: { groupName } })
//         .then(response => {
//             alert(response.data);
//             subscribeToGroup(groupName);  // Subscribe to WebSocket for this group
//         })
//         .catch(error => console.error('Error creating group:', error));
// }

// // Function to join a group
// function joinGroup() {
//     const groupName = document.getElementById("joinGroupName").value;
//     const username = document.getElementById("joinUsername").value;
//     axios.post(`${baseURL}/join`, null, { params: { groupName, username } })
//         .then(response => {
//             alert(response.data);
//             subscribeToGroup(groupName);  // Subscribe to WebSocket for this group
//             getUsers();  // Fetch and display updated user list
//         })
//         .catch(error => console.error('Error joining group:', error));
// }

// // Function to leave a group
// function leaveGroup() {
//     const groupName = document.getElementById("leaveGroupName").value;
//     const username = document.getElementById("leaveUsername").value;
//     axios.post(`${baseURL}/leave`, null, { params: { groupName, username } })
//         .then(response => {
//             alert(response.data);
//             getUsers();  // Fetch and display updated user list
//         })
//         .catch(error => console.error('Error leaving group:', error));
// }

// // Function to get users in a group
// function getUsers() {
//     const groupName = document.getElementById("displayGroupName").value;
//     axios.get(`${baseURL}/users`, { params: { groupName } })
//         .then(response => {
//             const users = response.data;
//             const userList = document.getElementById("userList");
//             userList.innerHTML = '';  // Clear previous list
//             if (users.length > 0) {
//                 users.forEach(user => {
//                     const listItem = document.createElement("li");
//                     listItem.textContent = user.username;
//                     userList.appendChild(listItem);
//                 });
//             } else {
//                 userList.innerHTML = "<li>No users found in this group.</li>";
//             }
//         })
//         .catch(error => console.error('Error fetching users:', error));
// }

// // Function to send a message to a group
// function sendMessage() {
//     const groupName = document.getElementById("messageGroupName").value;
//     const sender = document.getElementById("messageSender").value;
//     const content = document.getElementById("messageContent").value;
//     axios.post(`${baseURL}/send`, null, { params: { groupName, sender, content } })
//         .then(response => {
//             alert(response.data);
//         })
//         .catch(error => console.error('Error sending message:', error));
// }

// // Function to view messages in a group
// function viewMessages() {
//     const groupName = document.getElementById("viewGroupName").value;
//     axios.get(`${baseURL}/messages`, { params: { groupName } })
//         .then(response => {
//             const messages = response.data;
//             const messageList = document.getElementById("messageList");
//             messageList.innerHTML = '';  // Clear previous messages
//             if (messages.length > 0) {
//                 messages.forEach(message => {
//                     const listItem = document.createElement("li");
//                     listItem.innerHTML = `<strong>${message.sender}</strong>: ${message.content} 
//                                           <em>(${new Date(message.timestamp).toLocaleString()})</em>`;
//                     messageList.appendChild(listItem);
//                 });
//             } else {
//                 messageList.innerHTML = "<li>No messages found in this group.</li>";
//             }
//         })
//         .catch(error => console.error('Error fetching messages:', error));
// }

// // Function to display a received WebSocket message
// function showMessage(message) {
//     const response = document.getElementById("messageList");
//     const p = document.createElement("p");
//     p.appendChild(document.createTextNode(message.sender + ": " + message.content));
//     response.appendChild(p);
// }
const baseURL = 'http://localhost:8080/api/groups';
let stompClient = null;
let currentGroupName = '';  // Track the current group the user is in

// Connect to WebSocket server
function connect() {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
    });
}

window.onload = connect;

// // Subscribe to a group topic and listen for messages
// function subscribeToGroup(groupName) {
//     if (currentGroupName === groupName) return;  // Prevent subscribing multiple times

//     stompClient.subscribe(`/topic/${groupName}`, function (messageOutput) {
//         showMessage(JSON.parse(messageOutput.body));
//     });
//     currentGroupName = groupName;
// }

// Subscribe to a group topic and listen for messages and user list updates
function subscribeToGroup(groupName) {
    if (currentGroupName === groupName) return;  // Prevent subscribing multiple times

    // Subscribe to messages in the group
    stompClient.subscribe(`/topic/${groupName}`, function (messageOutput) {
        showMessage(JSON.parse(messageOutput.body));
    });

    // Subscribe to the updated list of users in the group
    stompClient.subscribe(`/topic/${groupName}/users`, function (usersOutput) {
        const users = JSON.parse(usersOutput.body);
        updateUserList(users);
    });

    currentGroupName = groupName;
}

// Function to update the user list in the UI
function updateUserList(users) {
    const userList = document.getElementById("userList");
    userList.innerHTML = '';  // Clear previous list
    if (users.length > 0) {
        users.forEach(user => {
            const listItem = document.createElement("li");
            listItem.textContent = user.username;
            userList.appendChild(listItem);
        });
    } else {
        userList.innerHTML = "<li>No users found in this group.</li>";
    }
}

// Create a new group
function createGroup() {
    const groupName = document.getElementById("createGroupName").value;
    axios.post(`${baseURL}/create`, null, { params: { groupName } })
        .then(response => {
            alert(response.data);
            subscribeToGroup(groupName);  // Subscribe to WebSocket for this group
        })
        .catch(error => console.error('Error creating group:', error));
}

// Join an existing group
function joinGroup() {
    const groupName = document.getElementById("joinGroupName").value;
    const username = document.getElementById("joinUsername").value;
    axios.post(`${baseURL}/join`, null, { params: { groupName, username } })
        .then(response => {
            alert(response.data);
            subscribeToGroup(groupName);  // Subscribe to WebSocket for this group
            getUsers();  // Fetch and display updated user list
        })
        .catch(error => console.error('Error joining group:', error));
}

// Leave a group
function leaveGroup() {
    const groupName = document.getElementById("joinGroupName").value;
    const username = document.getElementById("joinUsername").value;
    axios.post(`${baseURL}/leave`, null, { params: { groupName, username } })
        .then(response => {
            alert(response.data);
            getUsers();  // Fetch and display updated user list
        })
        .catch(error => console.error('Error leaving group:', error));
}

// Get the list of users in the group
function getUsers() {
    const groupName = document.getElementById("joinGroupName").value;
    axios.get(`${baseURL}/users`, { params: { groupName } })
        .then(response => {
            const users = response.data;
            const userList = document.getElementById("userList");
            userList.innerHTML = '';  // Clear previous list
            if (users.length > 0) {
                users.forEach(user => {
                    const listItem = document.createElement("li");
                    listItem.textContent = user.username;
                    userList.appendChild(listItem);
                });
            } else {
                userList.innerHTML = "<li>No users found in this group.</li>";
            }
        })
        .catch(error => console.error('Error fetching users:', error));
}

// Send a message to the group
function sendMessage() {
    const groupName = document.getElementById("messageGroupName").value;
    const sender = document.getElementById("messageSender").value;
    const content = document.getElementById("messageContent").value;
    
    // First, check if the user is in the group
    const userList = document.getElementById("userList");
    const userNamesInGroup = Array.from(userList.children).map(li => li.textContent); // Get all user names in the group
    
    if (!userNamesInGroup.includes(sender)) {
        alert("You are not a member of this group. You cannot send messages.");
        return;  // Stop the function if the user is not in the group
    }
    
    // Proceed to send the message if the user is in the group
    axios.post(`${baseURL}/send`, null, { params: { groupName, sender, content } })
        .then(response => {
            alert(response.data);
        })
        .catch(error => console.error('Error sending message:', error));
}


// Show the received message in the chat window
function showMessage(message) {
    const messageList = document.getElementById("messageList");
    const listItem = document.createElement("li");
    listItem.classList.add('message');
    listItem.innerHTML = `
        <span class="user">${message.sender}</span>: 
        <span class="content">${message.content}</span> 
        <span class="timestamp">(${new Date(message.timestamp).toLocaleString()})</span>
    `;
    messageList.appendChild(listItem);
}
