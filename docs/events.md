# Events
This page defines the events, their structure and which ones are sent when.

## Structure
The events are sent as JSON and there are basically two kinds of events: ProfileEvents and StripEvents.
The kinds are separated by the channel/topic they're sent on. ProfileEvents go to "profile" and StripEvents to "ledstrip"

They both share the same structure:

```
{
    "type": <Type>,
    "state": <Object>,
    "id": <Number>
}
```

### Type Field
The type field indicates the action and can be either "SAVE" or "DELETE".

### State Field
The state field carries an object that may be (depending on the kind of the event) either a ColorProfile or a LEDStrip (including the referenced colorprofile as embedded object) definition.
This field can be omitted, if the type is "DELETE".

### ID field
The id field is the ID of the object that it had before the action was executed. 
Typically, the ID should be `null` if the object did not exist before ("create" operation). It should be the same like the one in the "state"-object, if it was an update (which also sends a "SAVE" event) and it should be the id of the object that it had before a delete.


## Profile Event
A profile event is an event that gets send out, if a color profile is created, updated or deleted.
The implementation should send an event to the "profile" channel for a 
* CREATE of a color profile: containing the color profile as state, `null` as id and type "SAVE"
* UPDATE of a color profile: containing the color profile as state, the ID of the profile as id and type "SAVE"
* DELETE of a color profile: `null` as state, the ID of the profile before save as id and type "DELETE"

## Strip Event
A strip event is an event that gets send out, if a led strip is created, updated or deleted.
The implementation should send an event to the "ledstrip" channel for a 
* CREATE of a led strip: containing the led strip as state, `null` as id and type "SAVE"
* UPDATE of a led strip: containing the led strip ( and profile as embedded, if set) as state, the ID of the strip as id and type "SAVE"
* DELETE of a led strip: `null` as state, the ID of the strip before save as id and type "DELETE"
* UPDATE of a profile reference for a led strip: containing the led strip ( and profile as embedded) as state, the ID of the strip as id and type "SAVE"
* DELETE of a profile reference for a led strip: containing the led strip (and profile set to `null`) as state, the ID of the strip as id and type "SAVE"
