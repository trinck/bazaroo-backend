export interface Media {

  id : number,

  // Mime type of the media (e.g. "image/png", "image/jpeg", "video/mp4")
  type : string,

  // Name of the media (e.g. "my-image.png")
  name : string,

  url : string,

  path : string,

  size : number
}
