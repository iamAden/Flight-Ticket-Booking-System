const flight = {
    id: 1,
    company: "Airasia",
    // other flight details
  };
  
  let imgsrc;
  
  // Use a switch or if-else statements to determine the imgsrc based on flight.company
  switch (flight.company) {
    case "Airasia":
      imgsrc = "images/airasialogo.jpg";
      break;
    case "OtherAirline":
      imgsrc = "images/otherairlinelogo.jpg";
      break;
    // Add more cases for other airlines
    default:
      imgsrc = "images/defaultlogo.jpg"; // Default image if no match
  }
  
  // Use imgsrc in your HTML or create an element with the img tag
  const imgElement = `<img src="${imgsrc}" alt="Airline Logo">`;
  