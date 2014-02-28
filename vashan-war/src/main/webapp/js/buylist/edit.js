//AUTO_COMPLETE_DELAY = 1000;

function start() {
}

function listItem_keyDown(event) {
    var searchField = $("#list-title-input");
    $.ajax({
        url: ROOT + "/item/search/" + encodeURI(searchField.value),
        type: "GET",
        dataType: "json",
        success: function (suggesions) {
            var autoCompleteDiv = $("#autoComplete");
            autoCompleteDiv.clear();
            suggesions.forEach(function(item) {
                autoCompleteDiv.append($('<div class="list-autocomplete-item">' + item.title + '</div>'));
            });
        }
    });
//    if (event.which == 13 || event.keyCode == 13) {
//        var rowItem = JSON.stringify({
//            "date": new Date(),
//            "title": event.target.value
//        });
//        $.ajax({
//            url: ROOT + "/list/save.json",
//            type: "POST",
//            data: rowItem,
//            contentType:"application/json",
//            dataType:"json",
//            success: function (created) {
//                var newItem = $("<div class='list-item'/>");
//                newItem.html(itemString(created));
//                newItem.insertAfter($(".list-add"));
//                event.target.select();
//            }
//        });
//        return false;
//    }
//    return true;
}
