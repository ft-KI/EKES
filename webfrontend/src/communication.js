 export default class Communication{
    constructor(url) {
        this.url=url;
    }
    request(url2){
        var request = new XMLHttpRequest();
        request.open("GET",this.url+url2,false);
        request.send(null);
        return request.responseText;
    }
     getWorld(){
        return this.request("/getWorld");
    }
     getActors(){
        return this.request("/getActors");
    }
    getActorsbyId(id){
        return this.request("/getActorById?="+id);
    }

    getParams() {

        return this.request("/getParams")

    }

}

