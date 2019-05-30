# RSGui
Runescape themed gui library for Tribot


It is quite simple to create GUI's for Tribot with this gui system; It is designed similarly to JFrame.
It currently supports:
- Frame
- Panel
- Box ( Panel with padding )
- Scroll Panel
- Dropdown box
- Check box
- Outline box ( draws an outline around parent node )
- Text Label
- Text Button
- Image button
- Runescape fonts ( regular and bold )


# If you would like to JUST use the runescape fonts for your projects, you can access them easily with:
RSGuiRes.FONT_BOLD.drawStringShadow( graphics, "Hello World!" + ChatColor.GREEN + " Green text!", 0, 0 );
RSGuiRes.FONT_REGULAR.drawString( graphics, "Smaller text. No shadow.", 0, 16 );
