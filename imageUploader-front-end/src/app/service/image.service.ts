import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { Byte } from '@angular/compiler/src/util';
export class Image{
  constructor(
    public id:string,
    public imageName:string,
    public imageByte:Byte[],
    public thumbByte:Byte[],
    public thumbName:string,
  ) {}
}

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  response:any;
  baseUrl:String = "http://localhost:8080/api/v1/images/"
  constructor(private httpClient: HttpClient) { }
  
  postImage(uploadImageData:FormData){
      console.log("start image uploading")
      this.httpClient.post(this.baseUrl + 'upload', uploadImageData, { observe: 'response' })
          .subscribe((response) => {
              if (response.status === 200) {
                console.log('Image uploaded successfully');
              } else {
                console.log('Image not uploaded successfully');
              }
          }
      );
  }  

  getImage() {
    return this.httpClient.get<Image[]>(this.baseUrl + 'get');
  }
}
