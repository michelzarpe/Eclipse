 function updateClock (){
  Ext.fly('temporizadorSessao').update(new Date().format('g:i:s'));
} 	

var task = {run: updateClock, interval: 1000}
var runner = new Ext.util.TaskRunner();
	runner.start(task);

	
	


	
	
	