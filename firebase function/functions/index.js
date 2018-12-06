const functions = require('firebase-functions');
var admin = require("firebase-admin");
admin.initializeApp();
exports.sendFeedNotification = functions.database.ref("/Feeds/{feedId}").onCreate((snapshot) => {
 const feed=snapshot.val();
 const payload=
 {
 	"data":{
 		"title":feed.feedTitle,
 		"imageUrl":feed.feedImageUrl,
 		"feedId":feed.feedId
 	}
 };
 return admin.messaging().sendToTopic("feeds",payload);
});
