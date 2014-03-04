CommandsHandler.instance().registerCommand(JavaAdapter(Command, {
    name : function(){
        return "sleep";
    },
    perform: function(args, asker){
        var time = 1;
        if(args.length > 1)
            time = args[1];
        
        asker.write("Sleeping " + time + "sec");
        
        Thread.sleep(time * 1000);
    }
}));