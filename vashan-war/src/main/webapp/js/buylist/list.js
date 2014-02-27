function start() {
    $( "#list-title-input" ).keydown(listItem_keyDown);
    $.get(ROOT + '/list/search.json', gotItems);
}

function gotItems(items) {
    items.forEach(function (i) {
        var item = $("<div class='list-item'/>");
        item.html(itemString(i));
        $( "#buyList-container" ).append(item);
    });
}

function itemString(item) {
    return '[' + new Date(item.date).toString('dd.MM.yyyy') + '] ' + item.title;
}

function listItem_keyDown(event) {
    if (event.which == 13 || event.keyCode == 13) {
        var rowItem = JSON.stringify({
            "date": new Date(),
            "title": event.target.value
        });
        $.ajax({
            url: ROOT + "/list/save.json",
            type: "POST",
            data: rowItem,
            contentType:"application/json",
            dataType:"json",
            success: function (created) {
//                var newItem = $("<div class='list-item'/>");
//                newItem.html(itemString(created));
//                newItem.insertAfter($(".list-add"));
//                event.target.select();
                window.location = "list/edit/"+created.id;
            }
        });
        return false;
    }
    return true;
}
