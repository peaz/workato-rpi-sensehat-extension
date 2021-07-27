{
  title: 'Raspberry Pi SenseHAT',
  secure_tunnel: true,
  
  connection: {
    fields: [{ name: 'profile', hint: 'On-prem Raspberry Pi SenseHAT connection profile' }],
    authorization: { type: 'none'}
  },
  
  test: lambda do |connection|
    get("http://localhost/ext/#{connection['profile']}/ping").headers('X-Workato-Connector': 'enforce')
  end,
  
  actions: {
    
    clear: {
      title: 'Clear LED Matrix',
      description: 'Clear the LED Matrix display.',
      
      output_fields: lambda do |object_definitions| 
        [{name: 'status', type: :string}]
      end,
      
      execute: lambda do |connection|
        get("http://localhost/ext/#{connection['profile']}/clear").headers('X-Workato-Connector': 'enforce')
      end
    },
    
    environmentalSensor: {
      title: 'Get environmental sensor reading',
      description: 'Get humidity, temperature and pressure reading from the SenseHAT enviromental sensor.',
      
      output_fields: lambda do |object_definitions| 
        [{name: 'humidity', type: :float}, {name: 'temperature', type: :float}, {name: 'pressure', type: :float}]
      end,
      
      execute: lambda do |connection|
        get("http://localhost/ext/#{connection['profile']}/environmentalSensor").headers('X-Workato-Connector': 'enforce')
      end
    },
    
    displayMessage: {
      title: 'Display message',
      description: 'Display a message on the SenseHAT LED Matrix',
      
      input_fields: lambda do |object_definitions| 
        [
          {name: 'message', label: 'Message', type: :string, optional: false},
          {name: 'messageColor', label: 'Message Color', type: :string, pick_list: 'colors', control_type: 'select', optional: false},
          {name: 'backgroundColor', label: 'Background Color', type: :string, pick_list: 'colors', control_type: 'select', optional: false}
        ]
      end,
      output_fields: lambda do |object_definitions|
        [{name: 'status'}]
      end,
      
      execute: lambda do |connection, input|
        post("http://localhost/ext/#{connection['profile']}/displayMessage", input).headers('X-Workato-Connector': 'enforce')
      end
    },
    
    displayLetter: {
      title: 'Display a letter',
      description: 'Display a letter on the SenseHAT LED Matrix',
      
      input_fields: lambda do |object_definitions|
        [{name: 'letter', label: 'Letter', type: :string, optional: false}, 
        {name: 'letterColor', label: 'Letter Color', type: :string, pick_list: 'colors', control_type: 'select', optional: false},
        {name: 'backgroundColor', label: 'Background Color', type: :string, pick_list: 'colors', control_type: 'select', optional: false}]
      end, 
      output_fields: lambda do |object_definitions|
        [{name: 'status'}] 
      end,
        
      execute: lambda do |connection, input|
        post("http://localhost/ext/#{connection['profile']}/displayLetter", input).headers('X-Workato-Connector': 'enforce')
      end
    },
      
    color: {
      title: 'Set LED Color',
      description: 'Display an color based on the RGB value on the SenseHAT LED Matrix',
      
      input_fields: lambda do |object_definitions|
        [{name: 'color', label: 'Color', type: :string, control_type: 'select', pick_list: 'colors', optional: false}]
      end,
      output_fields: lambda do |object_definitions|
        [{name: 'status'}]
      end,
      
      execute: lambda do |connection, input|
        post("http://localhost/ext/#{connection['profile']}/displayColor", input).headers('X-Workato-Connector': 'enforce')
      end
    },
    
    displayStopIcon: {
      title: 'Display a stop sign',
      description: 'Display a stop sign icon on the SenseHAT LED Matrix',
      
      output_fields: lambda do |object_definitions|
        [{name: 'status'}]
      end,
      
      execute: lambda do |connection, input|
        get("http://localhost/ext/#{connection['profile']}/displayStopIcon", input).headers('X-Workato-Connector': 'enforce')
      end
    },
    
    displayCheckIcon: {
      title: 'Display a check sign',
      description: 'Display a check sign icon on the SenseHAT LED Matrix',
      
      output_fields: lambda do |object_definitions|
        [{name: 'status'}]
      end,
      
      execute: lambda do |connection, input|
        get("http://localhost/ext/#{connection['profile']}/displayCheckIcon", input).headers('X-Workato-Connector': 'enforce')
      end
    } 

  },
  pick_lists: {
    colors: lambda do |connection|
      [
        ["Red", "RED"],
        ["Green", "GREEN"],
        ["Blue", "BLUE"],
        ["Black", "BLACK"],
        ["White", "WHITE"],
        ["Yellow", "YELLOW"],
        ["Orange", "ORANGE"],
        ["Purple", "PURPLE"],
        ["Cyan", "CYAN"],
        ["Pink", "PINK"]
    ]
    end
  }

}
