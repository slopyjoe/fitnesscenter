class UserReport
	constructor: (opt) ->
		@modalEl = opt.modal
		@inputEl = opt.input
	
	showModal:() =>
		@modalEl.show('true')
		
	searchForId:() =>
		id = @inputEl.val()
		$.get(
			"/userreport/"+id,
			{},
			(data) ->
				alert 'we have data'
		)