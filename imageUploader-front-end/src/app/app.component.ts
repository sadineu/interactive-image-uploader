import { Component , ElementRef, OnInit } from '@angular/core';
import { ImageService } from './service/image.service'
import {WebSocketService} from "./service/web-socket.service";
import { ViewChild } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'imageUploader';
  topic: string = "/app/notify";
  selectedFile: any ;
  imageName: any;
  images:any
  stompClient:any
  public notification = 0;
  selectedFiles:string [] = []
  fileNames:string [] = []
  @ViewChild('myInput')
  myInputVariable: any;

  constructor(private imageService: ImageService, private webSocketService:WebSocketService) {
      this.stompClient = webSocketService.connect()
      const _this = this;
      _this.stompClient.connect({}, function (frame:any) {
          _this.stompClient.subscribe("/topic/notification", function (sdkEvent:any) {
            _this.handleMessage(sdkEvent);
          });
      }, webSocketService.errorCallBack);
   }
  
  ngOnInit():void {    
    this.getImage()
  }

  public onFileChanged(event:any) {
      for (var i = 0; i < event.target.files.length; i++) { 
        this.selectedFiles.push(event.target.files[i]);
        this.fileNames.push(event.target.files[i].name)
    }
  }

  reset() {
    this.myInputVariable.nativeElement.value = "";
    this.selectedFiles = [];
  }
  
  onUpload() {
    this.fileNames = []
    const uploadImageData = new FormData();
    for (var i = 0; i < this.selectedFiles.length; i++) { 
      uploadImageData.append("imageFiles", this.selectedFiles[i]);
    }
    this.reset()
    this.imageService.postImage(uploadImageData)
  }
 
  getImage(): void {
    this.notification = 0
    this.imageService.getImage().subscribe(
      response => this.handleSuccessfulResponse(response)
    );

  }

  handleSuccessfulResponse(response:any){
    this.images = response
  }

  handleMessage(message:any){
    this.notification ++ 
  }
    
}
