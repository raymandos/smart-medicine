document.addEventListener('DOMContentLoaded', function () {
    function updateRole(userId) {
        let roleSelect = document.getElementById('role-' + userId);
        if (roleSelect) {
            let selectedRole = roleSelect.value;

            fetch('/admin/updateRole', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id: userId, role: selectedRole })
            }).then(response => {
                if (response.ok) {
                    alert('Role updated successfully!');
                } else {
                    alert('Failed to update role.');
                }
            });
        } else {
            console.error('Element not found: role-' + userId);
        }
    }

    // Expose functions to the global scope
    window.updateRole = updateRole;
    window.deleteUser = deleteUser;
});