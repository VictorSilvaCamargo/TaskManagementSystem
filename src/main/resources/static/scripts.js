document.addEventListener('DOMContentLoaded', function() {
    const taskForm = document.getElementById('taskForm');
    const taskList = document.getElementById('taskList');

    const apiUrl = '/api/tasks';

    function loadTasks() {
        fetch(apiUrl)
            .then(response => response.json())
            .then(tasks => {
                taskList.innerHTML = '';
                tasks.forEach(task => renderTask(task));
            })
            .catch(error => console.error('Error fetching tasks:', error));
    }

    function renderTask(task) {
        const taskItem = document.createElement('li');
        taskItem.innerHTML = `
            <strong>${task.title}</strong>
            <p>${task.description}</p>
            <button class="status">${task.status === 'PENDING' ? 'Complete' : 'Pending'}</button>
            <button class="delete">Delete</button>
        `;
        taskItem.classList.add(task.status.toLowerCase());

        if (task.status === 'PENDING') {
            taskItem.querySelector('.status').addEventListener('click', () => {
                toggleTaskStatus(task.id, task.status);
            });

            taskItem.querySelector('.delete').addEventListener('click', () => {
                deleteTask(task.id);
            });
        } else {
            taskItem.querySelector('.status').remove();
            taskItem.querySelector('.delete').remove();
        }

        if (task.status === 'PENDING') {
            taskItem.classList.add('pending');
        } else if (task.status === 'COMPLETED') {
            taskItem.classList.add('completed');
        }

        taskList.appendChild(taskItem);
    }

    function addTask(title, description) {
        const newTask = {
            title: title,
            description: description,
            status: 'PENDING'
        };

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newTask)
        })
            .then(response => {
                if (response.status === 403) {
                    alert('Tasks can only be created on weekdays.');
                    return;
                }
                return response.json();
            })
            .then(createdTask => {
                if (createdTask) {
                    renderTask(createdTask);
                }
            })
            .catch(error => console.error('Error adding task:', error));
    }

    function toggleTaskStatus(taskId, currentStatus) {
        const updatedStatus = currentStatus === 'PENDING' ? 'COMPLETED' : 'PENDING';

        fetch(`${apiUrl}/${taskId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ status: updatedStatus })
        })
            .then(response => {
                if (response.status === 403) {
                    alert('Only pending tasks can be updated.');
                    return;
                }
                return response.json();
            })
            .then(updatedTask => {
                if (updatedTask) {
                    loadTasks();
                }
            })
            .catch(error => console.error('Error updating task:', error));
    }


    function deleteTask(taskId) {
        fetch(`${apiUrl}/${taskId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.status === 403) {
                    alert('Only pending tasks can be deleted.');
                    return;
                }
                loadTasks();
            })
            .catch(error => console.error('Error deleting task:', error));
    }

    taskForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const title = this.title.value;
        const description = this.description.value;

        if (title.trim() === '') {
            alert('Please enter a title for the task.');
            return;
        }

        addTask(title, description);
        this.reset();
    });

    loadTasks();
});
