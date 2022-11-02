package com.vaske.restaurants.util

/**
// Since the api returns this link https://loremflickr.com/500/500/restaurant for restaurant logo",
// which is a redirect, Coil seems to cache the first image and then all other restaurants have the same image
// had no time to hunt down why that is happening so this is a hacky workaround
// plus we avoid any unexpected (unwanted :D) pictures
// some random pics from images.google.com
 */

val listOfRestaurantImages = listOf(
    "https://www.brasseriesixty6.com/wordpress/wp-content/uploads/2020/11/DSC_5720a.jpg",
    "https://cdnimg.webstaurantstore.com/uploads/blog/2018/5/popup-restaurant-blog-1.jpg",
    "https://cdn.trendhunterstatic.com/thumbs/simple-restaurant.jpeg?auto=webp",
    "https://static.thatsup.co/content/img/place/p/o/user-photo/e997a993-thumb.jpg",
    "https://media1.agfg.com.au/images/listing/387/gallery/harbourfront-restaurant-11.jpg",
    "https://static-prod.dineplan.com/api/images/restaurants/1469/koi-restaurant555.jpg?d=1597843113",
    "https://static-prod.dineplan.com/api/images/restaurants/280/marble-restaurant509.jpg?d=1616164426",
    "https://individualrestaurants.com/wp-content/uploads/2021/03/piccolino15.jpg",
    "https://www.tommybahama.com/content/dam/tommy/restaurants/jupiter/2021/Restaurant_JupiterFL_Image2_Desktop.png",
    "https://ottolenghi.co.uk/pub/media/contentmanager/content/NOPI%201_1.jpg",
    "https://netstorage-briefly.akamaized.net/images/539adc5cd1beab86.jpg",
    "https://images.squarespace-cdn.com/content/v1/5ba9207734c4e264b1f3bd87/ab2035ce-7d49-4e5b-a22b-39415855cc3f/Tusk+bar+and+dining.jpg",
    "https://www.thetrainline.com/cms/media/5688/restaurant-in-florence-italy.jpg?mode=crop&width=800&height=800&quality=70",
    "https://i.etsystatic.com/25305238/r/il/f886c5/2560736878/il_fullxfull.2560736878_mzum.jpg",
    "https://guldsmedenhotels.com/wp-content/uploads/2022/04/1x1-Bryggen-Breakfast-3.jpg",
    "https://maketimetoseetheworld.com/wp-content/uploads/2015/12/Bambu-Seminyak-Restaurants-to-suit-any-budget.jpeg",
    "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/22/ac/73/7e/caption.jpg?w=800&h=-1&s=1"
)

fun randomRestaurantImage() = listOfRestaurantImages.random()

val emojis = listOf(
    "\uD83C\uDF60",
    "\uD83C\uDF5D",
    "\uD83C\uDF5B",
    "\uD83C\uDF5A",
    "\uD83E\uDD61",
    "\uD83C\uDF72",
    "\uD83E\uDD6A",
    "\uD83C\uDF5E",
    "\uD83E\uDD68",
    "\uD83C\uDF5F",
    "\uD83C\uDF57",
    "\uD83C\uDF56",
    "\uD83C\uDF2F",
    "\uD83C\uDF2E",
)

fun randomRestaurantEmoji() = emojis.random()
