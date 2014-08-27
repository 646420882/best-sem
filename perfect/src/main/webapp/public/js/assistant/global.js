$("tbody").delegate("tr","click", function (event) {
    $(this).parent().find("tr").removeClass("list2_box3");
    $(this).addClass("list2_box3");
});