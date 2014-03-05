    function start() {
        var $list = $("#list-title-input");
        $list.keypress(listItem_keyPressed);
        $list.autocomplete({
            select: function(event, ui) {
                onEnterDown(event.target, ui.item.value);
            },
            source: function(request, response) {
                $.ajax({
                    url: ROOT + "/item/search/" + encodeURI(request.term) + ".json",
                    type: "GET",
                    dataType:"json",
                    success: function (created) {
                        response(created);
                    }
                });
            }
        })
        .data( "ui-autocomplete" )._renderItem = function( ul, item ) {
            return $( "<li>" )
                .append( "<a>" + item.value + "</a>" )
                .appendTo( ul );
        };

    }

    function listItem_keyPressed(event) {
        if (event.which == 13 || event.keyCode == 13) {
            onEnterDown(event.target, event.target.value);
        }
    }

    function onEnterDown(field, value) {
        $.ajax({
            url: ROOT + "/item/findOrSave/" + encodeURI(value) + ".json",
            type: "POST",
            data: JSON.stringify({
                title: value
            }),
            contentType:"application/json",
            dataType: "json",
            success: function(createdItem) {
                var $suggestBox = $("#list-title-input");
                $("<div class='list-item'>" + createdItem.title + "</div>").insertAfter($suggestBox);
                field.value = "";
                $suggestBox.autocomplete("close");
            }
        });
    }
