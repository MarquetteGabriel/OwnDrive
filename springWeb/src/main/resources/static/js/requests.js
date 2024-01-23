$(document).ready(function() {

    fetchData($('#dataList').data('path'));

    function fetchData(relativePath) {
        var apiUrl = (relativePath) ? '/api/getFromPath?path=' + encodeURIComponent(relativePath) : '/api/all';

        $.ajax({
            url: 'http://localhost:11900' + apiUrl,
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                displayData(data);
            },
            error: function(error) {
                console.log('Erreur lors de la récupération des données de l\'API:', error);
            }
        });
    }


    // Fonction pour afficher les données dans la page
    function displayData(data) {
        var dataList = $('#dataList');
        dataList.empty();
        var tableHtml = '<table>' +
                            '<tr>' +
                                '<th>Type</th>' +
                                '<th>Nom</th>' +
                                '<th>Taille</th>' +
                                '<th>Dernière Modification</th>' +
                                '<th></th>' +
                            '</tr>';

        $.each(data, function(index, item) {
            tableHtml += '<tr>' +
                            '<td>' + item.type + '</td>' +
                            '<td>' + getFileNameDisplay(item) + '</td>' +
                            '<td>' + formatSize(item.size) + '</td>' +
                            '<td>' + formatModifiedDate(item.modifiedAt) + '</td>' +
                            '<td><a href="' + item.downloadUrl + '" target="_blank">Télécharger</a></td>' +
                        '</tr>';
        });

        tableHtml += '</table>';
        dataList.append(tableHtml);
    }

    function getFileNameDisplay(item) {
        if (item.type === 'rep') {
            '<a href="#" class="folder-link" data-path="' + item.name + '">' + item.name + '</a>';
        } else {
            return item.name;
        }
    }

    function formatSize(bytes) {
        if (bytes === 0) return '';
        const k = 1024;
        const sizes = ['o', 'Ko', 'Mo', 'Go', 'To'];

        const i = parseInt(Math.floor(Math.log(bytes) / Math.log(k)));

        return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    }

    function formatModifiedDate(isoDate) {
        const date = new Date(isoDate);
        const formattedDate = date.toLocaleDateString('fr-FR', { day: 'numeric', month: 'short', year: 'numeric' });
        const formattedHeure = date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
        return ('le ' + formattedDate + ' à ' + formattedHeure);
    }
});

$('#dataList').on('click', '.folder-link', function(event) {
    event.preventDefault();
    var path = $(this).data('path');
    fetchData(path);
});