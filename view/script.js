const url = "http://localhost:8080/task/user/1"

function hideLoad()
{
    document.getElementById("loading").style.display = "none";
}

async function showTable(tasks)
{
    const table = document.getElementById("tasks");

    for(let task of tasks)
    {
        const row = table.insertRow();
        const cel1 = row.insertCell(0);
        const cel2 = row.insertCell(1);
        const cel3 = row.insertCell(2);
        const cel4 = row.insertCell(3);

        cel1.innerHTML = task.id;
        cel2.innerHTML = task.description;
        cel3.innerHTML = task.user.username;
        cel4.innerHTML = task.user.id;

    }
}

async function getAPI(url)
{
    const response = await fetch(url, {method: "GET"});



    if(response.ok)
    {
        let data = await response.json();
        hideLoad();
        showTable(data);
    }
    else
    {
        console.error("Erro a solicitacao da API");
    }


}

getAPI(url);
