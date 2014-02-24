function start() {
    $.get('../../list/get/' + window.location.hash, gotItem);
}

function gotItem(item) {
    $(".head").html(item.title);
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
            url: "../../list/save.json",
            type: "POST",
            data: rowItem,
            contentType:"application/json",
            dataType:"json",
            success: function (created) {
                var newItem = $("<div class='list-item'/>");
                newItem.html(itemString(created));
                newItem.insertAfter($(".list-add"));
                event.target.select();
            }
        });
        return false;
    }
    return true;
}
