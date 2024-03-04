
		function appendResultOnScreen(response){
			
			const resultData = `${response.empId1}, ${response.empId2}, ${response.overlappingDuration};`;
	        const outputDiv = document.getElementById('report-result');
			outputDiv.innerHTML = resultData;
		}


		function createTableRows(projectData){
		
			for (key in projectData) {
			  
			  appendRow(projectData[key].empId, projectData[key].projectId, projectData[key].dateFrom, projectData[key].dateTo);
			
			}

		}


		function appendRow(empId, projectId, dateFrom, dateTo){
		
		    var newRow = document.createElement('tr');

			   newRow.innerHTML = '\
				<th scope="row">' + empId + '</th>\
				<td>' + projectId + '</td>\
				<td>' + dateFrom + '</td>\
				<td>' + dateTo + '</td>';

            document.getElementById('dataTable').getElementsByTagName('tbody')[0].appendChild(newRow);
		
		}

		async function getReportData() {
			try {
				const response = await fetch('http://localhost:8080/api/v1/assignment-team/get-report', {
					method: 'GET'
				});

				if (!response.ok) {
					throw new Error(`HTTP error! Status: ${response.status}`);
				}

				const data = await response.json();
				appendResultOnScreen(data);
				await getAllProjects(data.empId1, data.empId2);
			} catch (error) {
				console.error('Error getReportData:', error);
			}
		}

		async function getAllProjects(emp1, emp2) {
			try {
				const params = new URLSearchParams();
				params.append('emp1', emp1);
				params.append('emp2', emp2);

				const response = await fetch('http://localhost:8080/api/v1/assignment-team/get-projects?' + params.toString(), {
					method: 'GET'
				});

				if (!response.ok) {
					throw new Error(`HTTP error! Status: ${response.status}`);
				}

				const data = await response.json();
				createTableRows(data);
			} catch (error) {
				console.error('Error getAllProjects:', error);
			}
		}

		async function uploadFile(file) {
			try {
				if (file) {
					const formData = new FormData();
					formData.append('file', file);

					const response = await fetch('http://localhost:8080/api/v1/assignment-team/upload', {
						method: 'POST',
						body: formData
					});

					if (!response.ok) {
						throw new Error(`HTTP error! Status: ${response.status}`);
					}

					await getReportData();
				} else {
					alert('Please choose a file to upload.');
				}
			} catch (error) {
				console.error('Error uploading file:', error);
			}
		}
