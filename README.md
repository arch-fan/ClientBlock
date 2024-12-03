# ClientBlock

A Fabric Mod for blocking/filtering Minecraft clients (JUST SERVER SIDE!).

## Configuration

The configuration file is created automatically inside your Fabric Mods
configurations folder. The file is named `clientblock.json` which has
this structure:
```jsonc
{
  "action": "block", // "block" or "allow" (block by default)
  "clients": [
    "fabric"
  ], // Array of clients to block or allow (based in action mode) (empty by default)
  "kickMessage": "Custom kick message", // The client is not allowed! ("The client is not allowed!" by default)
  "logger": false // Where to log every single person client entering at the server (false by default)
}
```

Here you can copy the template without comments :)
```json
{
  "action": "block",
  "clients": [
    "fabric"
  ],
  "kickMessage": "Custom kick message",
  "logger": false
}
```

## Commands
`/clients` for listing all clients.


## Publication

Actually, the mod is published in two mod providers
- [X] https://www.curseforge.com/minecraft/mc-mods/clientblock
- [X] https://modrinth.com/mod/clientblock
