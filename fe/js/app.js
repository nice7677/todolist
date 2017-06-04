(function(window) {
    'use strict';

    // Your starting point. Enjoy the ride!
    $(document).ready(
        start(), page(),

    );

    function start() {
        $.ajax({
            url: 'http://localhost:8080/api/todos',
            dataType: 'json',
            success: function(data) {
                var now = $.now();
                var count = data.length;
                var todo = [count];
                var id = [count];
                var completed = [count];
                for (var i = 0; i < count; i++) {
                    todo[i] = data[i].todo;
                    completed[i] = data[i].completed;
                    id[i] = data[i].id;
                }
                for (var i = count - 1; i >= 0; i--) {
                    if (completed[i] == '0') {
                        $('ul.todo-list').append(
                            '<li id="' + id[i] + '"><div class="view"><input id="' + id[i] +
                            '" class="toggle" type="checkbox"><label id="' + 'l' + id[i] + '" >' +
                            todo[i] +
                            '</label><button  id="' + id[i] + '" class="destroy"></button></div></li>'
                        );
                    } else if (completed[i] == '1') {
                        $('ul.todo-list').append(
                            '<li id="' + id[i] + '" class="completed"><div class="view"><input id="' + id[i] +
                            '" class="toggle" type="checkbox" checked><label id="' + 'l' + id[i] + '" >' +
                            todo[i] +
                            '</label><button  id="' + id[i] + '"  class="destroy"></button></div></li>'
                        );
                    }
                }
                $(".new-todo").keydown('click', function(key) {
                    var todo = $(this).prop('value');
                    if (key.keyCode == 13) {
                        if (todo == "") { alert('할일을 입력해주세요.') } else {
                            var jdata = { "todo": todo, "date": now };
                            $.ajax({
                                url: 'http://localhost:8080/api/todos/',
                                method: 'post',
                                data: JSON.stringify(jdata),
                                contentType: 'application/json',
                                success: function(data) {
                                    $.ajax({
                                        url: 'http://localhost:8080/api/todos',
                                        dataType: 'json',
                                        success: function(data) {
                                            $('.new-todo').change().val("")
                                            var count = data.length - 1
                                            var id = data[count].id
                                            var todo = data[count].todo
                                            var listcount = $('.todo-list').children().length
                                            var insert = '<li id="' + id + '"><div class="view"><input id="' + id +
                                                '" class="toggle" type="checkbox"><label id="' + 'l' + id + '" >' +
                                                todo +
                                                '</label><button  id="' + id + '" class="destroy"></button></div></li>';
                                            if (listcount == 0) {
                                                $('.todo-list').append(insert);
                                            } else {
                                                $(insert).insertBefore($('.todo-list').children().first());
                                            }
                                            $('.filters > li > a.selected').removeClass();
                                            $('#all').addClass('selected');
                                            $('.todo-count > strong').html(count);
                                            page()
                                        }
                                    })
                                }
                            })
                        }
                    }
                });
            }
        })
    }

    function page() {
        $.ajax({
            url: 'http://localhost:8080/api/todos',
            dataType: 'json',
            success: function(data) {
                var now = $.now();
                var count = data.length;
                var todo = [count];
                var id = [count];
                var completed = [count];
                var comcount = 0;
                var uncomcount = 0;
                for (var i = 0; i < count; i++) {
                    todo[i] = data[i].todo;
                    completed[i] = data[i].completed;
                    id[i] = data[i].id;
                    if (completed[i] == 0) {
                        comcount++;
                    } else {
                        uncomcount++;
                    }
                }
                $('.filters > li > a.selected').removeClass();
                $('#all').addClass('selected');
                for (var i = 0; i < count; i++) {
                    if (completed[i] == 0) {
                        $('#' + id[i]).show();
                    } else {
                        $('#' + id[i]).show();
                    }
                }
                $(".todo-count > strong").html(count);
                $("input:checkbox").on("click").unbind("click").bind("click", function() {
                        var id = $(this).prop('id');
                        var todo = $("#l" + id).text();
                        var jdata = { "todo": todo, "completed": 1, "date": now }
                        $.ajax({
                            url: 'http://localhost:8080/api/todos/' + id,
                            method: 'put',
                            data: JSON.stringify(jdata),
                            contentType: 'application/json',
                            success: function(data) {
                                $('#' + id).addClass('completed');
                                page()
                            }
                        })      
                });
                $(".destroy").unbind("click").bind("click", function() {
                        var id = $(this).prop('id');
                        $.ajax({
                            url: 'http://localhost:8080/api/todos/' + id,
                            method: 'delete',
                            dataType: 'json',
                            success: function(data) {
                                $('#' + id).remove();
                                page();
                            }
                        })
                    }
                );
                $('#all').click(function() {
                    $('.filters > li > a.selected').removeClass();
                    $('#all').addClass('selected');
                    for (var i = 0; i < count; i++) {
                        if (completed[i] == 0) {
                            $('#' + id[i]).show();
                        } else {
                            $('#' + id[i]).show();
                        }
                    }
                    $('.todo-count > strong').html(count);
                })
                $('#active').click(function() {
                    $('.filters > li > a.selected').removeClass();
                    $('#active').addClass('selected');
                    for (var i = 0; i < count; i++) {
                        if (completed[i] == 0) {
                            $('#' + id[i]).show();
                        } else {
                            $('#' + id[i]).hide();
                        }
                    }
                    $('.todo-count > strong').html(comcount);
                })
                $('#completed').click(function() {
                    $('.filters > li > a.selected').removeClass();
                    $('#completed').addClass('selected');
                    for (var i = 0; i < count; i++) {
                        if (completed[i] == 1) {
                            $('#' + id[i]).show();
                        } else {
                            $('#' + id[i]).hide();
                        }
                    }
                    $('.todo-count > strong').html(uncomcount);
                })
                $('.clear-completed').unbind("click").bind("click", function(){
                    $.ajax({
                            url: 'http://localhost:8080/api/todos/completed/delete',
                            method: 'delete',
                            dataType: 'json',
                            success: function(data) {
                                $('.todo-list').children('li.completed').remove();
                                page();
                            }
                        })
                })
            }
        })
    }
})(window);