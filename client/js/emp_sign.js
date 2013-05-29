var addActivity = function(id, name, description){

	return '<div class=\"accordion-group\" id=\"activities\">'+
                  '<div class=\"accordion-heading\">'+
                   '<a class=\"accordion-toggle collapsed\" data-toggle=\"collapse\" data-parent=\"#accordion2\" href=\"#'+id+'\">'+
                      name+
                    '</a>'+
                  '</div>'+
                  '<div id=\"'+id+'\" class=\"accordion-body collapse\" style=\"height: 0px;\">'+
                    '<div class="accordion-inner">'+
                      description+
                    '</div>'+
                  '</div>'+
                '</div>';

};

$("body").keypress(function(e){

	if(e.which == 13){
		var $activites = $('.activities').length;
		alert("Num Activities " +$activites );
		$('#accordion2')
			.append(addActivity(1, 'Cardio', 'Run!!!!!'));
	}

});

$('.btnGrad').click(function(e){
	var emp_id = $('input[id=emp_id]').val();
	alert($(this).attr('value') + ' ' + emp_id);
	
	
	
});
