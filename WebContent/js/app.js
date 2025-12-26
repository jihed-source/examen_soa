// FILE: WebContent/js/app.js
const API_BASE_URL = 'http://localhost:8080/Exam_SOA_V2/exercice/revision/api';

// Show alert message
function showAlert(message, type = 'info') {
    const alertContainer = document.getElementById('alert-container');
    const alert = document.createElement('div');
    alert.className = `alert alert-${type} alert-dismissible fade show`;
    alert.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    alertContainer.appendChild(alert);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (alert.parentNode === alertContainer) {
            alertContainer.removeChild(alert);
        }
    }, 5000);
}

// Show specific section and hide others
function showSection(sectionId) {
    // Hide all sections
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        section.style.display = 'none';
    });
    
    // Show selected section
    document.getElementById(sectionId).style.display = 'block';
    
    // Load data if it's the list section
    if (sectionId === 'list-section') {
        loadAllPersons();
    }
}

// Load all persons
async function loadAllPersons() {
    try {
        const response = await fetch(`${API_BASE_URL}/persons`);
        if (!response.ok) {
            throw new Error('Failed to fetch persons');
        }
        
        const persons = await response.json();
        displayPersons(persons, 'persons-list');
    } catch (error) {
        console.error('Error loading persons:', error);
        showAlert('Failed to load persons: ' + error.message, 'danger');
    }
}

// Display persons in a table
function displayPersons(persons, containerId) {
    const container = document.getElementById(containerId);
    
    if (!persons || persons.length === 0) {
        container.innerHTML = '<div class="alert alert-info">No persons found.</div>';
        return;
    }
    
    let html = `
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Last Name</th>
                    <th>First Name</th>
                    <th>Age</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
    `;
    
    persons.forEach(person => {
        html += `
            <tr>
                <td>${person.id}</td>
                <td>${person.nom || ''}</td>
                <td>${person.prenom || ''}</td>
                <td>${person.age || ''}</td>
                <td>${person.email || ''}</td>
                <td>${person.telephone || ''}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="editPerson(${person.id})">Edit</button>
                    <button class="btn btn-sm btn-danger" onclick="deletePerson(${person.id})">Delete</button>
                </td>
            </tr>
        `;
    });
    
    html += `
            </tbody>
        </table>
    `;
    
    container.innerHTML = html;
}

// Add new person
document.getElementById('add-person-form').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const person = {
        nom: document.getElementById('nom').value,
        prenom: document.getElementById('prenom').value,
        age: document.getElementById('age').value ? parseInt(document.getElementById('age').value) : null,
        email: document.getElementById('email').value,
        telephone: document.getElementById('telephone').value
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/persons`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(person)
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showAlert(result.message, 'success');
            document.getElementById('add-person-form').reset();
            loadAllPersons();
        } else {
            showAlert(result.message, 'danger');
        }
    } catch (error) {
        console.error('Error adding person:', error);
        showAlert('Failed to add person: ' + error.message, 'danger');
    }
});

// Edit person (open modal)
async function editPerson(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/persons/${id}`);
        if (!response.ok) {
            throw new Error('Failed to fetch person data');
        }
        
        const person = await response.json();
        
        // Fill the form
        document.getElementById('edit-id').value = person.id;
        document.getElementById('edit-nom').value = person.nom || '';
        document.getElementById('edit-prenom').value = person.prenom || '';
        document.getElementById('edit-age').value = person.age || '';
        document.getElementById('edit-email').value = person.email || '';
        document.getElementById('edit-telephone').value = person.telephone || '';
        
        // Show modal
        const modal = new bootstrap.Modal(document.getElementById('editModal'));
        modal.show();
    } catch (error) {
        console.error('Error loading person for edit:', error);
        showAlert('Failed to load person data: ' + error.message, 'danger');
    }
}

// Update person
async function updatePerson() {
    const id = document.getElementById('edit-id').value;
    
    const person = {
        nom: document.getElementById('edit-nom').value,
        prenom: document.getElementById('edit-prenom').value,
        age: document.getElementById('edit-age').value ? parseInt(document.getElementById('edit-age').value) : null,
        email: document.getElementById('edit-email').value,
        telephone: document.getElementById('edit-telephone').value
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/persons/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(person)
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showAlert(result.message, 'success');
            
            // Close modal
            const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
            modal.hide();
            
            // Refresh list
            loadAllPersons();
        } else {
            showAlert(result.message, 'danger');
        }
    } catch (error) {
        console.error('Error updating person:', error);
        showAlert('Failed to update person: ' + error.message, 'danger');
    }
}

// Delete person
async function deletePerson(id) {
    if (!confirm('Are you sure you want to delete this person?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/persons/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showAlert('Person deleted successfully', 'success');
            loadAllPersons();
        } else {
            const result = await response.json();
            showAlert(result.message, 'danger');
        }
    } catch (error) {
        console.error('Error deleting person:', error);
        showAlert('Failed to delete person: ' + error.message, 'danger');
    }
}

// Search person
async function searchPerson() {
    const searchTerm = document.getElementById('search-input').value.trim();
    
    if (!searchTerm) {
        showAlert('Please enter a name to search', 'warning');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/persons/search/${encodeURIComponent(searchTerm)}`);
        if (!response.ok) {
            throw new Error('Failed to search persons');
        }
        
        const persons = await response.json();
        displayPersons(persons, 'search-results');
        
        if (persons.length === 0) {
            showAlert('No persons found with that name', 'info');
        }
    } catch (error) {
        console.error('Error searching persons:', error);
        showAlert('Failed to search persons: ' + error.message, 'danger');
    }
}

// Clear search
function clearSearch() {
    document.getElementById('search-input').value = '';
    document.getElementById('search-results').innerHTML = '';
}

// Initialize the app
document.addEventListener('DOMContentLoaded', function() {
    // Load persons on page load
    loadAllPersons();
});