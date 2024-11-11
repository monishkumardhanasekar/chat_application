const baseURL = 'http://localhost:8080/api/groups';
let stompClient = null;

function connect() {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
    });
}

window.onload = connect;

// WebSocket function to subscribe to a specific group and listen for messages
function subscribeToGroup(groupName) {
    stompClient.subscribe(`/topic/${groupName}`, function (messageOutput) {
        showMessage(JSON.parse(messageOutput.body));
    });
}

// Function to create a group
function createGroup() {
    const groupName = document.getElementById("createGroupName").value;
    axios.post(`${baseURL}/create`, null, { params: { groupName } })
        .then(response => {
            alert(response.data);
            subscribeToGroup(groupName);  // Subscribe to WebSocket for this group
        })
        .catch(error => console.error('Error creating group:', error));
}

// Function to join a group
function joinGroup() {
    const groupName = document.getElementById("joinGroupName").value;
    const username = document.getElementById("joinUsername").value;
    axios.post(`${baseURL}/join`, null, { params: { groupName, username } })
        .then(response => {
            alert(response.data);
            subscribeToGroup(groupName);  // Subscribe to WebSocket for this group
        })
        .catch(error => console.error('Error joining group:', error));
}

// Function to leave a group
function leaveGroup() {
    const groupName = document.getElementById("leaveGroupName").value;
    const username = document.getElementById("leaveUsername").value;
    axios.post(`${baseURL}/leave`, null, { params: { groupName, username } })
        .then(response => alert(response.data))
        .catch(error => console.error('Error leaving group:', error));
}

// Function to get users in a group
function getUsers() {
    const groupName = document.getElementById("displayGroupName").value;
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

// Function to send a message to a group
function sendMessage() {
    const groupName = document.getElementById("messageGroupName").value;
    const sender = document.getElementById("messageSender").value;
    const content = document.getElementById("messageContent").value;
    axios.post(`${baseURL}/send`, null, { params: { groupName, sender, content } })
        .then(response => {
            alert(response.data);
        })
        .catch(error => console.error('Error sending message:', error));
}

// Function to view messages in a group
function viewMessages() {
    const groupName = document.getElementById("viewGroupName").value;
    axios.get(`${baseURL}/messages`, { params: { groupName } })
        .then(response => {
            const messages = response.data;
            const messageList = document.getElementById("messageList");
            messageList.innerHTML = '';  // Clear previous messages
            if (messages.length > 0) {
                messages.forEach(message => {
                    const listItem = document.createElement("li");
                    listItem.innerHTML = `<strong>${message.sender}</strong>: ${message.content} 
                                          <em>(${new Date(message.timestamp).toLocaleString()})</em>`;
                    messageList.appendChild(listItem);
                });
            } else {
                messageList.innerHTML = "<li>No messages found in this group.</li>";
            }
        })
        .catch(error => console.error('Error fetching messages:', error));
}

// Function to display a received WebSocket message
function showMessage(message) {
    const response = document.getElementById("messageList");
    const p = document.createElement("p");
    p.appendChild(document.createTextNode(message.sender + ": " + message.content));
    response.appendChild(p);
}