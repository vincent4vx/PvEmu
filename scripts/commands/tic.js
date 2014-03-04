CommandsHandler.instance().registerCommand(JavaAdapter(Command, {
    name : function(){
        return "tic";
    },
    perform: function(args, asker){
        var time = 1;
        if(args.length > 1)
            time = args[1];
        
        new Thread({
            run: function(){
                var B = true;
                for(var i = 0; i < time; ++i){
                    asker.write(B ? "tic" : "tac");
                    B = !B;
                    Thread.sleep(1000);
                }
            }
        }).start();
    }
}));