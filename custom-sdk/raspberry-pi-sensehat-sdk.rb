{
  title: 'Raspberry Pi SenseHAT',
  secure_tunnel: true,

  connection: {
    fields: [{ name: 'profile', hint: 'On-prem Raspberry Pi SenseHAT connection profile' }],
    authorization: { type: 'none'}
  },

  test: ->(connection) {
    get("http://localhost/ext/#{connection['profile']}/ping").headers('X-Workato-Connector': 'enforce')
  },

  actions: {
    
    clear: {
      title: 'Clear LED Matrix',
      description: 'Clear the LED Matrix display.',

      output_fields: ->(_) { [{name: 'status', type: :string}] },

      execute: ->(connection) {
        get("http://localhost/ext/#{connection['profile']}/clear").headers('X-Workato-Connector': 'enforce')
      }
    },
    
    humidity: {
      title: 'Get humidity reading',
      description: 'Get humidity reading from the SenseHAT enviromental sensor.',

      output_fields: ->(_) { [{name: 'humidity', type: :float}] },

      execute: ->(connection) {
        get("http://localhost/ext/#{connection['profile']}/humidity").headers('X-Workato-Connector': 'enforce')
      }
    },
    
    temperature: {
      title: 'Get temperature reading',
      description: 'Get temperature reading from the SenseHAT enviromental sensor.',

      output_fields: ->(_) { [{name: 'temperature', type: :float}] },

      execute: ->(connection) {
        get("http://localhost/ext/#{connection['profile']}/temperature").headers('X-Workato-Connector': 'enforce')
      }
    },
    
    displayMessage: {
      title: 'Display message',
      description: 'Display a message on the SenseHAT LED Matrix',

      input_fields: ->(_) { [{name: 'message', type: :string, optional: false}] },
      output_fields: ->(_) { [{name: 'status'}] },

      execute: ->(connection, input) {
        post("http://localhost/ext/#{connection['profile']}/displayMessage", input).headers('X-Workato-Connector': 'enforce')
      }
    },
    
    color: {
      title: 'Set LED Color',
      description: 'Display an color based on the RGB value on the SenseHAT LED Matrix',

      input_fields: ->(_) { [{name: 'r', label: 'R', type: :integer, optional: false}, {name: 'g', label: 'G', type: :integer, optional: false},{name: 'b', label: 'B', type: :integer, optional: false}] },
      output_fields: ->(_) { [{name: 'status'}] },

      execute: ->(connection, input) {
        post("http://localhost/ext/#{connection['profile']}/color", input).headers('X-Workato-Connector': 'enforce')
      }
    }
  
  
  }
}
